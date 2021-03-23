package com.prodnees.dto;

import com.prodnees.config.constants.LocalConstants;
import com.prodnees.domain.rels.ObjectRightType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductRightDto {
    @Positive(message = "productId is a required field must be a positive number")
    private int productId;
    @NotBlank(message = "email cannot be blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private ObjectRightType objectRightType;

    public int getProductId() {
        return productId;
    }

    public ProductRightDto setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProductRightDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public ProductRightDto setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
