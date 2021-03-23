package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.action.rel.ProductRightAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.controller.insecure.SignupController;
import com.prodnees.domain.Product;
import com.prodnees.domain.rels.ObjectRightType;
import com.prodnees.domain.rels.ProductRight;
import com.prodnees.dto.ProductDto;
import com.prodnees.dto.ProductRightDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.service.ProductService;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.util.MapperUtil;
import com.prodnees.util.ValidatorUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static com.prodnees.config.constants.APIErrors.ACCESS_DENIED;
import static com.prodnees.config.constants.APIErrors.EMAIL_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.UPDATE_DENIED;
import static com.prodnees.web.response.LocalResponse.configure;


@RestController
@RequestMapping("/secure")
@CrossOrigin
@Transactional
public class ProductController {
    private final ProductService productService;
    private final UserValidator userValidator;
    private final UserAction userAction;
    private final ProductRightAction productRightAction;
    private final AssociatesService associatesService;

    public ProductController(ProductService productService,
                             UserValidator userValidator,
                             UserAction userAction,
                             ProductRightAction productRightAction,
                             AssociatesService associatesService) {
        this.productService = productService;
        this.userValidator = userValidator;
        this.userAction = userAction;
        this.productRightAction = productRightAction;
        this.associatesService = associatesService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> save(@Validated @RequestBody ProductDto dto,
                                  HttpServletRequest servletRequest) {
        dto.setId(0);
        int ownerId = userValidator.extractUserId(servletRequest);
        Product product = MapperUtil.getDozer().map(dto, Product.class);
        product = productService.save(product);
        productRightAction.save(new ProductRight()
                .setUserId(ownerId)
                .setProductId(product.getId())
                .setObjectRightsType(ObjectRightType.OWNER));
        return configure(product);
    }

    @GetMapping("/products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Assert.isTrue(productRightAction.findByProductIdAndUserId(integer, userId).isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(productService.getById(integer));
        }, () -> {
            Iterable<Integer> productIdIterable = productRightAction.getAllByUserId(userId).stream().map(ProductRight::getProductId).collect(Collectors.toList());
            atomicReference.set(productService.getAllByIds(productIdIterable));
        });

        return LocalResponse.configure(atomicReference.get());
    }


    @PutMapping("/product")
    public ResponseEntity<?> update(@Validated @RequestBody ProductDto dto,
                                    HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        Optional<ProductRight> batchProductRightsOpt = productRightAction.findByProductIdAndUserId(dto.getId(), userId);
        AtomicReference<Product> productAtomicReference = new AtomicReference<>();
        batchProductRightsOpt.ifPresentOrElse(productRights -> {
            Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRightType.OWNER), UPDATE_DENIED.getMessage());
            Product product = productService.getById(dto.getId());
            product.setName(dto.getName())
                    .setDescription(ValidatorUtil.ifValidOrElse(dto.getDescription(), product.getDescription()));
            productAtomicReference.set(productService.save(product));
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure(productAtomicReference.get());
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> delete(@RequestParam int id,
                                    HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        Optional<ProductRight> batchProductRightsOpt = productRightAction.findByProductIdAndUserId(id, userId);
        batchProductRightsOpt.ifPresentOrElse(productRights -> {
            Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRightType.OWNER), ACCESS_DENIED.getMessage());
            productService.deleteById(productRights.getProductId());
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure();
    }

    /**
     * <p>Save the {@link ProductRight}</p>
     * <p>Send email to the user for getting the rights</p>
     * <i>Only associates can be given ProductRights</i>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PostMapping("/product-rights")
    public ResponseEntity<?> addProductRights(@Validated @RequestBody ProductRightDto dto,
                                              HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        Assert.isTrue(dto.getObjectRightsType() != ObjectRightType.OWNER, "you can only assign an editor or a  viewer");
        Assert.isTrue(userAction.existsByEmail(dto.getEmail()),
                String.format(EMAIL_NOT_FOUND.getMessage(), dto.getEmail())
                        + String.format(". Invite them to signup at %s ",
                        MvcUriComponentsBuilder.fromController(SignupController.class).path("/user/signup").build().toString()));
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(userId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        Optional<ProductRight> optionalProductRights = productRightAction.findByProductIdAndUserId(dto.getProductId(), userId);
        Assert.isTrue(optionalProductRights.isPresent() && optionalProductRights.get().getObjectRightsType() == ObjectRightType.OWNER,
                "only owners can invite others to admin their product");
        return configure(productRightAction.save(dto));
    }

    /**
     * You must be the owner of the product
     * <p>the other user must not be the owner of the product</p>
     *
     * @param productId
     * @param userId
     * @param servletRequest
     * @return
     */
    @DeleteMapping("/product-rights")
    public ResponseEntity<?> deleteProductRights(@RequestParam int productId, @RequestParam int userId,
                                                 HttpServletRequest servletRequest) {
        int adminId = userValidator.extractUserId(servletRequest);
        Assert.isTrue(userId != adminId, "you cannot delete your own product rights");
        Optional<ProductRight> adminProductRightOpt = productRightAction.findByProductIdAndUserId(productId, adminId);//check you have permission
        Assert.isTrue(adminProductRightOpt.isPresent() && adminProductRightOpt.get().getObjectRightsType() == ObjectRightType.OWNER,
                UPDATE_DENIED.getMessage());
        Optional<ProductRight> userProductRightOpt = productRightAction.findByProductIdAndUserId(productId, userId);// check the other user is not the OWNER
        Assert.isTrue(userProductRightOpt.isPresent() && userProductRightOpt.get().getObjectRightsType() != ObjectRightType.OWNER,
                "you cannot remove another owner's product rights");
        productRightAction.deleteByProductIdAndUserId(productId, userId);
        return configure();

    }


}
