package com.prodnees.core.action.rel.impl;

import com.prodnees.auth.domain.User;
import com.prodnees.auth.service.UserService;
import com.prodnees.core.action.rel.BatchRightAction;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.rels.BatchRight;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.batch.BatchRightDto;
import com.prodnees.core.model.batch.BatchRightModel;
import com.prodnees.core.model.user.UserModel;
import com.prodnees.core.service.batch.BatchRightService;
import com.prodnees.core.service.batch.BatchService;
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
import java.util.stream.Collectors;

@Service
public class BatchRightActionImpl implements BatchRightAction {
    private final BatchRightService batchRightService;
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final BatchService batchService;
    private final LocalEmailService localEmailService;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    public BatchRightActionImpl(BatchRightService batchRightService,
                                UserService userService,
                                UserAttributesService userAttributesService,
                                BatchService batchService,
                                LocalEmailService localEmailService) {
        this.batchRightService = batchRightService;
        this.userService = userService;
        this.userAttributesService = userAttributesService;
        this.batchService = batchService;
        this.localEmailService = localEmailService;
    }

    @Override
    public BatchRightModel save(BatchRightDto rightsDto) {
        User user = userService.getByEmail(rightsDto.getEmail());
        BatchRight batchRight = batchRightService.findByBatchIdAndUserId(rightsDto.getBatchId(), user.getId())
                .orElseGet(() -> {
                    BatchRight batchRightNew = new BatchRight()
                            .setUserId(user.getId())
                            .setBatchId(rightsDto.getBatchId())
                            .setObjectRight(rightsDto.getObjectRightsType());

                    return batchRightService.save(batchRightNew);
                });

        batchRight.setObjectRight(rightsDto.getObjectRightsType());

        sendNewBatchRightsEmail(rightsDto.getEmail());
        return mapToModel(batchRight);
    }

    @Override
    public BatchRight save(BatchRight batchRight) {
        return batchRightService.save(batchRight);
    }

    @Override
    public BatchRight getByBatchIdAndUserId(int batchId, int ownerId) {
        return batchRightService.findByBatchIdAndUserId(batchId, ownerId)
                .orElseThrow(() -> new NeesNotFoundException(String.format("BatchRight with batchId: %d and userId: %d not found", batchId, ownerId)));
    }

    @Override
    public List<BatchRightModel> getAllByBatchId(int batchId) {
        List<BatchRight> batchRights = batchRightService.getAllByBatchId(batchId);
        return batchRights.stream().map(this::mapToModel).collect(Collectors.toList());
    }

    @Override
    public List<BatchRight> getAllByUserId(int ownerId) {
        return batchRightService.getAllByUserId(ownerId);
    }

    @Override
    public List<BatchRightModel> getAllModelByUserId(int ownerId) {
        List<BatchRight> batchRights = batchRightService.getAllByUserId(ownerId);
        return batchRights.stream().map(this::mapToModel).collect(Collectors.toList());
    }

    @Override
    public boolean hasBatchEditorRights(int batchId, int editorId) {
        return batchRightService.hasBatchEditorRights(batchId, editorId);
    }

    @Override
    public boolean hasBatchReaderRights(int batchId, int readerId) {
        return batchRightService.hasBatchReaderRights(batchId, readerId);
    }

    private BatchRightModel mapToModel(BatchRight productRights) {
        BatchRightModel model = new BatchRightModel();
        Batch batch = batchService.getById(productRights.getBatchId());
        UserAttributes userAttributes = userAttributesService.getByUserId(productRights.getUserId());
        return model.setBatchProduct(batch)
                .setUser(new UserModel().setId(userAttributes.getUserId())
                        .setEmail(userAttributes.getEmail())
                        .setFirstName(userAttributes.getFirstName())
                        .setLastName(userAttributes.getLastName()))
                .setObjectRightsType(productRights.getObjectRight());

    }

    @Override
    public boolean sendNewBatchRightsEmail(String email) {
        Map<String, Object> batchProductRightsMailMail = new HashMap<>();
        batchProductRightsMailMail.put(EmailPlaceHolders.TITLE, "Batch Product Right");
        batchProductRightsMailMail.put(EmailPlaceHolders.MESSAGE, "You have an update on a Batch Product.");
        try {
            localEmailService.sendTemplateEmail(email, "Batch Product Right", batchProductRightsMailMail);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            localLogger.error(e.getCause().getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void deleteByBatchIdAndUserId(int batchId, int userId) {
        batchRightService.deleteByBatchIdAndUserId(batchId, userId);

    }
}
