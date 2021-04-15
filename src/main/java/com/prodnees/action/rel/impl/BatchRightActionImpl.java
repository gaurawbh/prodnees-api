package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.domain.user.User;
import com.prodnees.domain.user.UserAttributes;
import com.prodnees.dto.batch.BatchRightDto;
import com.prodnees.model.batch.BatchRightModel;
import com.prodnees.model.user.UserModel;
import com.prodnees.service.batch.BatchService;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.service.user.UserAttributesService;
import com.prodnees.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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
        Optional<BatchRight> batchProductRightOpt = findByBatchIdAndUserId(rightsDto.getBatchId(), user.getId());
        AtomicReference<BatchRightModel> atomicReference = new AtomicReference<>();
        batchProductRightOpt.ifPresentOrElse(batchProductRight -> {
            batchProductRight.setObjectRightsType(rightsDto.getObjectRightsType());
            atomicReference.set(mapToModel(batchRightService.save(batchProductRight)));
        }, () -> {
            BatchRight batchRight = new BatchRight()
                    .setUserId(user.getId())
                    .setBatchId(rightsDto.getBatchId())
                    .setObjectRightsType(rightsDto.getObjectRightsType());
            atomicReference.set(mapToModel(batchRightService.save(batchRight)));
        });
        sendNewBatchRightsEmail(rightsDto.getEmail());
        return atomicReference.get();
    }

    @Override
    public BatchRight save(BatchRight batchRight) {
        return batchRightService.save(batchRight);
    }

    @Override
    public Optional<BatchRight> findByBatchIdAndUserId(int batchId, int ownerId) {
        return batchRightService.findByBatchIdAndUserId(batchId, ownerId);
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
                .setUserModel(new UserModel().setId(userAttributes.getUserId())
                        .setEmail(userAttributes.getEmail())
                        .setFirstName(userAttributes.getFirstName())
                        .setLastName(userAttributes.getLastName()))
                .setObjectRightsType(productRights.getObjectRightsType());

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
