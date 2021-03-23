package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.ProductRightAction;
import com.prodnees.domain.User;
import com.prodnees.domain.UserAttributes;
import com.prodnees.domain.rels.ProductRights;
import com.prodnees.dto.ProductRightDto;
import com.prodnees.model.ProductModel;
import com.prodnees.model.ProductRightModel;
import com.prodnees.model.UserModel;
import com.prodnees.service.ProductService;
import com.prodnees.service.UserAttributesService;
import com.prodnees.service.UserService;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.ProductRightsService;
import com.prodnees.util.MapperUtil;
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
        ProductRights productRights = productRightsService.save(new ProductRights()
                .setUserId(user.getId())
                .setProductId(dto.getProductId())
                .setObjectRightsType(dto.getObjectRightsType()));
        return mapToModel(productRights);
    }

    @Override
    public ProductRights save(ProductRights productRights) {
        return productRightsService.save(productRights);
    }

    @Override
    public boolean existsByProductIdAndUserId(int productId, int userId) {
        return productRightsService.existsByProductIdAndUserId(productId, userId);
    }

    @Override
    public Optional<ProductRights> findByProductIdAndUserId(int productId, int userId) {
        return productRightsService.findByProductIdAndUserId(productId, userId);
    }

    @Override
    public List<ProductRights> getAllByUserId(int userId) {
        return productRightsService.getAllByUserId(userId);
    }

    @Override
    public List<ProductRights> getAllByProductId(int productId) {
        return productRightsService.getAllByProductId(productId);
    }

    public ProductRightModel mapToModel(ProductRights productRights) {
        ProductRightModel productRightModel = new ProductRightModel();
        ProductModel productModel = MapperUtil.getDozer().map(productService.getById(productRights.getProductId()), ProductModel.class);
        UserAttributes userAttributes = userAttributesService.getByUserId(productRights.getUserId());
        return productRightModel.setProductModel(productModel)
                .setUserModel(new UserModel().setId(userAttributes.getUserId()).setEmail(userAttributes.getEmail()).setFirstName(userAttributes.getFirstName()).setLastName(userAttributes.getLastName()))
                .setObjectRightsType(productRights.getObjectRightsType());

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

}
