package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.BatchProductRightAction;
import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.User;
import com.prodnees.domain.UserAttributes;
import com.prodnees.domain.rels.BatchProductRight;
import com.prodnees.dto.BatchProductRightDto;
import com.prodnees.model.BatchProductRightModel;
import com.prodnees.model.UserModel;
import com.prodnees.service.BatchProductService;
import com.prodnees.service.UserAttributesService;
import com.prodnees.service.UserService;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.BatchProductRightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        BatchProductRight rights = new BatchProductRight()
                .setUserId(user.getId())
                .setBatchProductId(rightsDto.getBatchProductId())
                .setObjectRightsType(rightsDto.getObjectRightsType());
        sendNewBatchProductRightsEmail(rightsDto.getEmail());

        return mapToModel(batchProductRightService.save(rights));
    }

    @Override
    public BatchProductRight save(BatchProductRight batchProductRight) {
        return batchProductRightService.save(batchProductRight);
    }

    @Override
    public Optional<BatchProductRight> findByBatchProductIdAndOwnerId(int batchProductId, int ownerId) {
        return batchProductRightService.findByBatchProductIdAndOwnerId(batchProductId, ownerId);
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
    public List<BatchProductRightModel> getAllModelByOwnerId(int ownerId) {
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
        batchProductRightsMailMail.put(EmailPlaceHolders.TITLE, "New Batch Product Right");
        batchProductRightsMailMail.put(EmailPlaceHolders.MESSAGE, "You have been added to a new Batch Product.");
        try {
            localEmailService.sendTemplateEmail(email, "New Batch Product Right", batchProductRightsMailMail);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            localLogger.error(e.getCause().getMessage());
            e.printStackTrace();
            return false;
        }

    }
}
