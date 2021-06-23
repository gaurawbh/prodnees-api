package com.prodnees.core.dto.batch;

import com.prodnees.core.config.constants.LocalConstants;
import com.prodnees.core.domain.enums.ObjectRight;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class BatchRightDto {
    @Positive(message = "batchId is a required field must be a positive number")
    private int batchId;
    @NotBlank(message = "email cannot be blank")
    @Email(regexp = LocalConstants.EMAIL_REGEX, message = "invalid email format")
    private String email;
    private ObjectRight objectRight;

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

    public ObjectRight getObjectRightsType() {
        return objectRight;
    }

    public BatchRightDto setObjectRightsType(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
