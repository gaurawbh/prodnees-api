package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.BatchProductRightAction;
import com.prodnees.domain.batchproduct.BatchProduct;
import com.prodnees.domain.rels.BatchProductRight;
import com.prodnees.domain.user.User;
import com.prodnees.domain.user.UserAttributes;
import com.prodnees.dto.batchproduct.BatchProductRightDto;
import com.prodnees.model.BatchProductRightModel;
import com.prodnees.model.UserModel;
import com.prodnees.service.batchproduct.BatchProductService;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.BatchProductRightService;
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
public class BatchProductRightActionImpl implements BatchProductRightAction {
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    private final BatchProductRightService batchProductRightService;
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final BatchProductService batchProductService;
    private final LocalEmailService localEmailService;

    public BatchProductRightActionImpl(BatchProductRightService batchProductRightService,
                                       UserService userService,
                                       UserAttributesService userAttributesService,
                                       BatchProductService batchProductService,
                                       LocalEmailService localEmailService) {
        this.batchProductRightService = batchProductRightService;
        this.userService = userService;
        this.userAttributesService = userAttributesService;
        this.batchProductService = batchProductService;
        this.localEmailService = localEmailService;
    }

    @Override
    public BatchProductRightModel save(BatchProductRightDto rightsDto) {
        User user = userService.getByEmail(rightsDto.getEmail());
        Optional<BatchProductRight> batchProductRightOpt = findByBatchProductIdAndUserId(rightsDto.getBatchProductId(), user.getId());
        AtomicReference<BatchProductRightModel> atomicReference = new AtomicReference<>();
        batchProductRightOpt.ifPresentOrElse(batchProductRight -> {
            batchProductRight.setObjectRightsType(rightsDto.getObjectRightsType());
            atomicReference.set(mapToModel(batchProductRightService.save(batchProductRight)));
        }, () -> {
            BatchProductRight batchProductRight = new BatchProductRight()
                    .setUserId(user.getId())
                    .setBatchProductId(rightsDto.getBatchProductId())
                    .setObjectRightsType(rightsDto.getObjectRightsType());
            atomicReference.set(mapToModel(batchProductRightService.save(batchProductRight)));
        });
        sendNewBatchProductRightsEmail(rightsDto.getEmail());
        return atomicReference.get();
    }

    @Override
    public BatchProductRight save(BatchProductRight batchProductRight) {
        return batchProductRightService.save(batchProductRight);
    }

    @Override
    public Optional<BatchProductRight> findByBatchProductIdAndUserId(int batchProductId, int ownerId) {
        return batchProductRightService.findByBatchProductIdAndUserId(batchProductId, ownerId);
    }

    @Override
    public List<BatchProductRightModel> getAllByBatchProductId(int batchProductId) {
        List<BatchProductRight> batchProductRights = batchProductRightService.getAllByBatchProductId(batchProductId);
        return batchProductRights.stream().map(this::mapToModel).collect(Collectors.toList());
    }

    @Override
    public List<BatchProductRight> getAllByOwnerId(int ownerId) {
        return batchProductRightService.getAllByOwnerId(ownerId);
    }

    @Override
    public List<BatchProductRightModel> getAllModelByUserId(int ownerId) {
        List<BatchProductRight> batchProductRights = batchProductRightService.getAllByOwnerId(ownerId);
        return batchProductRights.stream().map(this::mapToModel).collect(Collectors.toList());
    }

    @Override
    public boolean hasBatchProductEditorRights(int batchProductId, int editorId) {
        return batchProductRightService.hasBatchProductEditorRights(batchProductId, editorId);
    }

    @Override
    public boolean hasBatchProductReaderRights(int batchProductId, int readerId) {
        return batchProductRightService.hasBatchProductReaderRights(batchProductId, readerId);
    }

    private BatchProductRightModel mapToModel(BatchProductRight productRights) {
        BatchProductRightModel model = new BatchProductRightModel();
        BatchProduct batchProduct = batchProductService.getById(productRights.getBatchProductId());
        UserAttributes userAttributes = userAttributesService.getByUserId(productRights.getUserId());
        return model.setBatchProduct(batchProduct)
                .setUserModel(new UserModel().setId(userAttributes.getUserId())
                        .setEmail(userAttributes.getEmail())
                        .setFirstName(userAttributes.getFirstName())
                        .setLastName(userAttributes.getLastName()))
                .setObjectRightsType(productRights.getObjectRightsType());

    }

    @Override
    public boolean sendNewBatchProductRightsEmail(String email) {
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
    public void deleteByBatchProductIdAndUserId(int batchProductId, int userId) {
        batchProductRightService.deleteByBatchProductIdAndUserId(batchProductId, userId);

    }
}
