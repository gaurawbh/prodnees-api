package com.prodnees.dto.batchproduct;

import com.prodnees.config.constants.LocalConstants;
import com.prodnees.domain.enums.ObjectRightType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class BatchRightDto {
    @Positive(message = "batchProductId is a required field must be a positive number")
    private int batchId;
    @NotBlank(message = "email cannot be blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private ObjectRightType objectRightType;

    public int getBatchId() {
        return batchId;
    }

    public BatchRightDto setBatchId(int batchId) {
        this.batchId = batchId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BatchRightDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public BatchRightDto setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
