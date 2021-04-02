package com.prodnees.dto;

import com.prodnees.config.constants.LocalConstants;
import com.prodnees.domain.enums.ObjectRightType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class BatchProductRightDto {
    @Positive(message = "batchProductId is a required field must be a positive number")
    private int batchProductId;
    @NotBlank(message = "email cannot be blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private ObjectRightType objectRightType;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductRightDto setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BatchProductRightDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public BatchProductRightDto setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
