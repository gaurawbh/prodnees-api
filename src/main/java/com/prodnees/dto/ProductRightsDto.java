package com.prodnees.dto;

import com.prodnees.config.constants.LocalConstants;
import com.prodnees.domain.rels.ObjectRightsType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductRightsDto {
    @Positive(message = "productId must be a positive number")
    private int productId;
    @NotBlank(message = "email cannot be blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private ObjectRightsType objectRightsType;

    public int getProductId() {
        return productId;
    }

    public ProductRightsDto setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProductRightsDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public ObjectRightsType getObjectRightsType() {
        return objectRightsType;
    }

    public ProductRightsDto setObjectRightsType(ObjectRightsType objectRightsType) {
        this.objectRightsType = objectRightsType;
        return this;
    }
}
