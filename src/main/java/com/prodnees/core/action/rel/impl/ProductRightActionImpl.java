package com.prodnees.core.action.rel.impl;

import com.prodnees.auth.domain.User;
import com.prodnees.auth.service.UserService;
import com.prodnees.core.action.rel.ProductRightAction;
import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.domain.rels.ProductRight;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.ProductRightDto;
import com.prodnees.core.model.ProductRightModel;
import com.prodnees.core.model.user.UserModel;
import com.prodnees.core.service.batch.ProductRightsService;
import com.prodnees.core.service.batch.ProductService;
import com.prodnees.core.service.email.EmailPlaceHolders;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductRightActionImpl implements ProductRightAction {
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    private final ProductRightsService productRightsService;
    private final LocalEmailService localEmailService;
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final ProductService productService;

    public ProductRightActionImpl(ProductRightsService productRightsService,
                                  LocalEmailService localEmailService,
                                  UserService userService,
                                  UserAttributesService userAttributesService,
                                  ProductService productService) {
        this.productRightsService = productRightsService;
        this.localEmailService = localEmailService;
        this.userService = userService;
        this.userAttributesService = userAttributesService;
        this.productService = productService;
    }

    @Override
    public ProductRightModel save(ProductRightDto dto) {
        User user = userService.getByEmail(dto.getEmail());
        ProductRight productRight = productRightsService.save(new ProductRight()
                .setUserId(user.getId())
                .setProductId(dto.getProductId())
                .setObjectRightsType(dto.getObjectRightsType()));
        return mapToModel(productRight);
    }

    @Override
    public ProductRight save(ProductRight productRight) {
        return productRightsService.save(productRight);
    }

    @Override
    public boolean existsByProductIdAndUserId(int productId, int userId) {
        return productRightsService.existsByProductIdAndUserId(productId, userId);
    }

    @Override
    public Optional<ProductRight> findByProductIdAndUserId(int productId, int userId) {
        return productRightsService.findByProductIdAndUserId(productId, userId);
    }

    @Override
    public ProductRight getByProductIdAndUserId(int productId, int userId) {
        return productRightsService.findByProductIdAndUserId(productId, userId)
                .orElseThrow(()-> new NeesNotFoundException(String.format("ProductRight with productId: %d and userId: %d not found", productId, userId)));
    }

    @Override
    public List<ProductRight> getAllByUserId(int userId) {
        return productRightsService.getAllByUserId(userId);
    }

    @Override
    public List<ProductRight> getAllByProductId(int productId) {
        return productRightsService.getAllByProductId(productId);
    }

    public ProductRightModel mapToModel(ProductRight productRight) {
        ProductRightModel productRightModel = new ProductRightModel();
        Product product = productService.getById(productRight.getProductId());
        UserAttributes userAttributes = userAttributesService.getByUserId(productRight.getUserId());
        return productRightModel.setProduct(product)
                .setUserModel(new UserModel()
                        .setId(userAttributes.getUserId())
                        .setEmail(userAttributes.getEmail())
                        .setFirstName(userAttributes.getFirstName())
                        .setLastName(userAttributes.getLastName()))
                .setObjectRight(productRight.getObjectRightsType());

    }

    @Override
    public boolean sendNewProductRightsEmail(String email) {
        Map<String, Object> productRightsMailMail = new HashMap<>();
        productRightsMailMail.put(EmailPlaceHolders.TITLE, "New Product Right");
        productRightsMailMail.put(EmailPlaceHolders.MESSAGE, "You have been added to a new Product.");
        try {
            localEmailService.sendTemplateEmail(email, "New Product Right", productRightsMailMail);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            localLogger.error(e.getCause().getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean hasProductEditorRight(int productId, int userId) {
            return productRightsService.hasProductEditorRight(productId, userId);
    }

    @Override
    public void deleteByProductIdAndUserId(int productId, int userId) {
        productRightsService.deleteByProductIdAndUserId(productId, userId);
    }

}
