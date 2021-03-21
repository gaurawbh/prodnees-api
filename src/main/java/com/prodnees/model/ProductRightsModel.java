package com.prodnees.model;

import com.prodnees.domain.rels.ObjectRightsType;
import java.util.List;

public class ProductRightsModel {

    private ProductModel productModel;
    private List<Integer> userIdList;
    private ObjectRightsType objectRightsType;

    public ProductModel getProductModel() {
        return productModel;
    }

    public ProductRightsModel setProductModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public ProductRightsModel setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
        return this;
    }

    public ObjectRightsType getObjectRightsType() {
        return objectRightsType;
    }

    public ProductRightsModel setObjectRightsType(ObjectRightsType objectRightsType) {
        this.objectRightsType = objectRightsType;
        return this;
    }
}
