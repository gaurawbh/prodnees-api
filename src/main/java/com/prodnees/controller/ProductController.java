package com.prodnees.controller;

import com.prodnees.action.ProductRightsAction;
import com.prodnees.action.UserAction;
import com.prodnees.controller.insecure.SignupController;
import com.prodnees.domain.Product;
import com.prodnees.domain.User;
import com.prodnees.domain.rels.ObjectRightsType;
import com.prodnees.domain.rels.ProductRights;
import com.prodnees.dto.ProductDto;
import com.prodnees.dto.ProductRightsDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.service.ProductService;
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
    private final ProductRightsAction productRightsAction;

    public ProductController(ProductService productService,
                             UserValidator userValidator,
                             UserAction userAction,
                             ProductRightsAction productRightsAction) {
        this.productService = productService;
        this.userValidator = userValidator;
        this.userAction = userAction;
        this.productRightsAction = productRightsAction;
    }

    @PostMapping("/product")
    public ResponseEntity<?> save(@Validated @RequestBody ProductDto dto,
                                  HttpServletRequest servletRequest) {
        dto.setId(0);
        int ownerId = userValidator.extractUserId(servletRequest);
        Product product = MapperUtil.getDozer().map(dto, Product.class);
        product = productService.save(product);
        productRightsAction.save(new ProductRights()
                .setUserId(ownerId)
                .setProductId(product.getId())
                .setObjectRightsType(ObjectRightsType.OWNER));
        return configure(product);
    }

    @GetMapping("/products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Assert.isTrue(productRightsAction.findByProductIdAndUserId(integer, userId).isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(productService.getById(integer));
        }, () -> {
            Iterable<Integer> productIdIterable = productRightsAction.getAllByUserId(userId).stream().map(ProductRights::getProductId).collect(Collectors.toList());
            atomicReference.set(productService.getAllByIds(productIdIterable));
        });

        return LocalResponse.configure(atomicReference.get());
    }


    @PutMapping("/product")
    public ResponseEntity<?> update(@Validated @RequestBody ProductDto dto,
                                    HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        Optional<ProductRights> batchProductRightsOpt = productRightsAction.findByProductIdAndUserId(dto.getId(), userId);
        AtomicReference<Product> productAtomicReference = new AtomicReference<>();
        batchProductRightsOpt.ifPresentOrElse(productRights -> {
            Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRightsType.OWNER), UPDATE_DENIED.getMessage());
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
        Optional<ProductRights> batchProductRightsOpt = productRightsAction.findByProductIdAndUserId(id, userId);
        batchProductRightsOpt.ifPresentOrElse(productRights -> {
            Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRightsType.OWNER), ACCESS_DENIED.getMessage());
            productService.deleteById(productRights.getProductId());
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure();
    }

    @PostMapping("/product-rights")
    public ResponseEntity<?> addProductRights(@Validated @RequestBody ProductRightsDto dto,
                                              HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        Assert.isTrue(userAction.existsByEmail(dto.getEmail()),
                String.format(EMAIL_NOT_FOUND.getMessage(), dto.getEmail())
                        + String.format(". Invite them to signup at %s ",
                        MvcUriComponentsBuilder.fromController(SignupController.class).path("/user/signup").build().toString()));
        Optional<ProductRights> optionalProductRights = productRightsAction.findByProductIdAndUserId(dto.getProductId(), userId);
        Assert.isTrue(optionalProductRights.isPresent() && optionalProductRights.get().getObjectRightsType() == ObjectRightsType.OWNER,
                "only owners can invite others to admin their product");
        //todo
        User user = userAction.getByEmail(dto.getEmail());
        return configure(productRightsAction.save(dto));

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
        Optional<ProductRights> adminProductRightsOpt = productRightsAction.findByProductIdAndUserId(productId, adminId);//check you have permission
        Assert.isTrue(adminProductRightsOpt.isPresent() && adminProductRightsOpt.get().getObjectRightsType() == ObjectRightsType.OWNER,
                UPDATE_DENIED.getMessage());
        Optional<ProductRights> userProductRightsOpt = productRightsAction.findByProductIdAndUserId(productId, userId);// check the other user is not the OWNER
        Assert.isTrue(userProductRightsOpt.isPresent() && userProductRightsOpt.get().getObjectRightsType() != ObjectRightsType.OWNER,
                "you cannot remove another owner's product rights");
        return configure();

    }


}
