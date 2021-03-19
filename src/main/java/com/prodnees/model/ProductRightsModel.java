package com.prodnees.model;

import com.prodnees.domain.Product;
import com.prodnees.domain.UserAttributes;
import com.prodnees.domain.rels.ObjectRightsType;

public class ProductRightsModel {

    private Product product;
    private UserAttributes userAttributes;
    private ObjectRightsType objectRightsType;

    public Product getProduct() {
        return product;
    }

    public ProductRightsModel setProduct(Product product) {
        this.product = product;
        return this;
    }

    public UserAttributes getUserAttributes() {
        return userAttributes;
    }

    public ProductRightsModel setUserAttributes(UserAttributes userAttributes) {
        this.userAttributes = userAttributes;
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
