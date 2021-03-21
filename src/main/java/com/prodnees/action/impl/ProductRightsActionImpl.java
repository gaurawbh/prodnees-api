package com.prodnees.action.impl;

import com.prodnees.action.ProductRightsAction;
import com.prodnees.domain.User;
import com.prodnees.domain.rels.ProductRights;
import com.prodnees.dto.ProductRightsDto;
import com.prodnees.model.ProductModel;
import com.prodnees.model.ProductRightsModel;
import com.prodnees.service.ProductService;
import com.prodnees.service.UserAttributesService;
import com.prodnees.service.UserService;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.ProductRightsService;
import com.prodnees.util.MapperUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductRightsActionImpl implements ProductRightsAction {
    private final ProductRightsService productRightsService;
    private final LocalEmailService localEmailService;
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final ProductService productService;

    public ProductRightsActionImpl(ProductRightsService productRightsService,
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
    public ProductRightsModel save(ProductRightsDto dto) {
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

    public ProductRightsModel mapToModel(ProductRights productRights) {
        ProductRightsModel productRightsModel = new ProductRightsModel();
        ProductModel productModel = MapperUtil.getDozer().map(productService.getById(productRights.getProductId()), ProductModel.class);
        List<Integer> userIdList = productRightsService.getAllByProductId(productRights.getProductId()).stream().map(ProductRights::getUserId).collect(Collectors.toList());
        return productRightsModel.setProductModel(productModel)
                .setUserIdList(userIdList)
                .setObjectRightsType(productRights.getObjectRightsType());

    }

}
