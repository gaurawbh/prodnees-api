package com.prodnees.core.controller;

import com.prodnees.auth.action.UserAction;
import com.prodnees.auth.controller.SignupController;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.rel.ProductRightAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.ProductRight;
import com.prodnees.core.dto.ProductDto;
import com.prodnees.core.dto.ProductRightDto;
import com.prodnees.core.service.batch.ProductService;
import com.prodnees.core.service.rels.AssociatesService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.prodnees.core.config.constants.APIErrors.ACCESS_DENIED;
import static com.prodnees.core.config.constants.APIErrors.EMAIL_NOT_FOUND;
import static com.prodnees.core.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.core.config.constants.APIErrors.UPDATE_DENIED;
import static com.prodnees.core.web.response.LocalResponse.configure;


@RestController
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
        return configure(productService.addNew(dto));
    }

    /**
     * if id is not provided, check the {@link ProductRight} return the {@link Product} the user has right to
     *
     * @param id
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id) {
        int userId = RequestContext.getUserId();
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        if (id.isPresent()) {
            Assert.isTrue(productRightAction.existsByProductIdAndUserId(id.get(), userId), OBJECT_NOT_FOUND.getMessage());
            return configure(productService.getById(id.get()));
        } else {
            Iterable<Integer> productIdIterable = productRightAction.getAllByUserId(userId).stream().map(ProductRight::getProductId).collect(Collectors.toList());
            return configure(productService.getAllByIds(productIdIterable));
        }
    }

    /**
     * User has to be {@link ObjectRight#full} or {@link ObjectRight#update} to update a {@link Product}
     * @param dto
     * @return
     */
    @PutMapping("/product")
    public ResponseEntity<?> update(@Validated @RequestBody ProductDto dto) {
        int userId = RequestContext.getUserId();
        Assert.isTrue(productRightAction.hasProductEditorRight(dto.getId(), userId), UPDATE_DENIED.getMessage());
        return configure(productService.update(dto));
    }

    /**
     * Only {@link ObjectRight#full} can delete a {@link Product}
     * @param id
     * @return
     */

    @DeleteMapping("/product")
    public ResponseEntity<?> delete(@RequestParam int id) {
        int userId = RequestContext.getUserId();
        ProductRight productRights = productRightAction.getByProductIdAndUserId(id, userId);
        Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRight.full), ACCESS_DENIED.getMessage());
        productService.deleteById(productRights.getProductId());
        return configure("successfully deleted product with id: " + id);
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
        Assert.isTrue(dto.getObjectRightsType() != ObjectRight.full, "you can only assign an editor or a  viewer");
        Assert.isTrue(userAction.existsByEmail(dto.getEmail()),
                String.format(EMAIL_NOT_FOUND.getMessage(), dto.getEmail())
                        + String.format(". Invite them to signup at %s ",
                        MvcUriComponentsBuilder.fromController(SignupController.class).path("/user/signup").build()));
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(userId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        Optional<ProductRight> optionalProductRights = productRightAction.findByProductIdAndUserId(dto.getProductId(), userId);
        Assert.isTrue(optionalProductRights.isPresent() && optionalProductRights.get().getObjectRightsType() == ObjectRight.full,
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
        Assert.isTrue(adminProductRightOpt.isPresent() && adminProductRightOpt.get().getObjectRightsType() == ObjectRight.full,
                UPDATE_DENIED.getMessage());
        Optional<ProductRight> userProductRightOpt = productRightAction.findByProductIdAndUserId(productId, userId);// check the other user is not the OWNER
        Assert.isTrue(userProductRightOpt.isPresent() && userProductRightOpt.get().getObjectRightsType() != ObjectRight.full,
                "you cannot remove another owner's product rights");
        productRightAction.deleteByProductIdAndUserId(productId, userId);
        return configure();

    }


}
