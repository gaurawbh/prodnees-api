package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.action.rel.ProductRightAction;
import com.prodnees.auth.SignupController;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.batch.Product;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.ProductRight;
import com.prodnees.dto.ProductDto;
import com.prodnees.dto.ProductRightDto;
import com.prodnees.filter.RequestContext;
import com.prodnees.service.batch.ProductService;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.util.MapperUtil;
import com.prodnees.util.ValidatorUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.prodnees.config.constants.APIErrors.*;
import static com.prodnees.web.response.LocalResponse.configure;


@RestController
@RequestMapping("/secure")
@CrossOrigin
@Transactional
public class ProductController {
    private final ProductService productService;
    private final UserAction userAction;
    private final ProductRightAction productRightAction;
    private final AssociatesService associatesService;

    public ProductController(ProductService productService,
                             UserAction userAction,
                             ProductRightAction productRightAction,
                             AssociatesService associatesService) {
        this.productService = productService;
        this.userAction = userAction;
        this.productRightAction = productRightAction;
        this.associatesService = associatesService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> save(@Validated @RequestBody ProductDto dto) {
        dto.setId(0);
        int ownerId = RequestContext.getUserId();
        Product product = MapperUtil.getDozer().map(dto, Product.class);
        product = productService.save(product);
        productRightAction.save(new ProductRight()
                .setUserId(ownerId)
                .setProductId(product.getId())
                .setObjectRightsType(ObjectRight.OWNER));
        return configure(product);
    }

    @GetMapping("/products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id) {
        int userId = RequestContext.getUserId();
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
    public ResponseEntity<?> update(@Validated @RequestBody ProductDto dto) {
        int userId = RequestContext.getUserId();
        ProductRight productRights = productRightAction.findByProductIdAndUserId(dto.getId(), userId)
                .orElseThrow(NeesNotFoundException::new);
        Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRight.OWNER), UPDATE_DENIED.getMessage());
        Product product = productService.getById(dto.getId());
        product.setName(dto.getName())
                .setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), product.getDescription()));
        return configure(productService.save(product));
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> delete(@RequestParam int id) {
        int userId = RequestContext.getUserId();
        ProductRight productRights = productRightAction.findByProductIdAndUserId(id, userId)
                .orElseThrow(NeesNotFoundException::new);
        Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRight.OWNER), ACCESS_DENIED.getMessage());
        productService.deleteById(productRights.getProductId());
        return configure("successfully deleted product");
    }

    /**
     * <p>Save the {@link ProductRight}</p>
     * <p>Send email to the user for getting the rights</p>
     * <i>Only associates can be given ProductRights</i>
     *
     * @param dto
     * @return
     */
    @PostMapping("/product-rights")
    public ResponseEntity<?> addProductRights(@Validated @RequestBody ProductRightDto dto) {
        int userId = RequestContext.getUserId();
        Assert.isTrue(dto.getObjectRightsType() != ObjectRight.OWNER, "you can only assign an editor or a  viewer");
        Assert.isTrue(userAction.existsByEmail(dto.getEmail()),
                String.format(EMAIL_NOT_FOUND.getMessage(), dto.getEmail())
                        + String.format(". Invite them to signup at %s ",
                        MvcUriComponentsBuilder.fromController(SignupController.class).path("/user/signup").build().toString()));
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(userId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        Optional<ProductRight> optionalProductRights = productRightAction.findByProductIdAndUserId(dto.getProductId(), userId);
        Assert.isTrue(optionalProductRights.isPresent() && optionalProductRights.get().getObjectRightsType() == ObjectRight.OWNER,
                "only owners can invite others to admin their product");
        return configure(productRightAction.save(dto));
    }

    /**
     * You must be the owner of the product
     * <p>the other user must not be the owner of the product</p>
     *
     * @param productId
     * @param userId
     * @return
     */
    @DeleteMapping("/product-rights")
    public ResponseEntity<?> deleteProductRights(@RequestParam int productId, @RequestParam int userId) {
        int adminId = RequestContext.getUserId();
        Assert.isTrue(userId != adminId, "you cannot delete your own product rights");
        Optional<ProductRight> adminProductRightOpt = productRightAction.findByProductIdAndUserId(productId, adminId);//check you have permission
        Assert.isTrue(adminProductRightOpt.isPresent() && adminProductRightOpt.get().getObjectRightsType() == ObjectRight.OWNER,
                UPDATE_DENIED.getMessage());
        Optional<ProductRight> userProductRightOpt = productRightAction.findByProductIdAndUserId(productId, userId);// check the other user is not the OWNER
        Assert.isTrue(userProductRightOpt.isPresent() && userProductRightOpt.get().getObjectRightsType() != ObjectRight.OWNER,
                "you cannot remove another owner's product rights");
        productRightAction.deleteByProductIdAndUserId(productId, userId);
        return configure();

    }


}
