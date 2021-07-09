package com.prodnees.core.service.impl;

import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.dao.batch.BatchDao;
import com.prodnees.core.dao.batch.ProductDao;
import com.prodnees.core.dao.doc.NeesDocDao;
import com.prodnees.core.dao.rels.ProductRightsDao;
import com.prodnees.core.dao.stage.StageDao;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.domain.rels.ProductRight;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.model.NeesObjProps;
import com.prodnees.core.service.NeesDocumentService;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.LocalStringUtils;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NeesDocumentServiceImpl implements NeesDocumentService {
    private final NeesDocDao neesDocDao;
    private final BatchDao batchDao;
    private final StageDao stageDao;
    private final ProductDao productDao;
    private final ProductRightsDao productRightsDao;
    private final NeesObjectRightService neesObjectRightService;


    public NeesDocumentServiceImpl(NeesDocDao neesDocDao,
                                   BatchDao batchDao,
                                   StageDao stageDao,
                                   ProductDao productDao,
                                   ProductRightsDao productRightsDao,
                                   NeesObjectRightService neesObjectRightService) {
        this.neesDocDao = neesDocDao;
        this.batchDao = batchDao;
        this.stageDao = stageDao;
        this.productDao = productDao;
        this.productRightsDao = productRightsDao;
        this.neesObjectRightService = neesObjectRightService;
    }

    @Override
    public NeesDoc save(NeesDoc neesDoc) {
        return neesDocDao.save(neesDoc);
    }

    @Override
    public NeesDoc getById(int id) {
        return neesDocDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Document with id: %d not found", id)));
    }

    @Override
    public NeesDoc getByName(String name) {
        return neesDocDao.getByName(name);
    }

    @Override
    public boolean existsByName(String name) {
        return neesDocDao.existsByName(name);
    }

    @Override
    public boolean existsById(int id) {
        return neesDocDao.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        neesDocDao.deleteById(id);
    }

    @Override
    public int getNextId() {
        return neesDocDao.getNextId(CurrentTenantResolver.getTenant(), "nees_doc", "id");
    }

    @Override
    public List<Map<String, Object>> getValidDocObjects(int id) {
        NeesDoc neesDoc = neesDocDao.getById(id);
        List<Map<String, Object>> docObjects = new ArrayList<>();
        if (!LocalStringUtils.hasValue(neesDoc.getObjectType())) {
            return docObjects;
        } else {
            return getValidDocObjects(neesDoc.getObjectType());
        }

    }

    private List<Map<String, Object>> getValidDocObjects(String objectType) {
        int userId = RequestContext.getUserId();
        List<Map<String, Object>> docObjects = new ArrayList<>();
        Map<String, Object> docObject = new HashMap<>();
        switch (objectType) {
            case "Batch":
                List<Batch> batches = batchDao.findAll();
                batches.forEach(batch -> {
                    docObject.put(NeesObjProps.id.name(), batch.getId());
                    docObject.put(NeesObjProps.id.name(), batch.getName());
                    docObjects.add(docObject);
                });
            case "Stage":
                throw new NeesBadRequestException("Adding Stages from the document is not yet supported. Add it's document from the Stage page");
            case "Product":
                List<ProductRight> productRights = productRightsDao.getAllByUserId(userId);
                List<Integer> productIds = productRights.stream().map(ProductRight::getProductId).collect(Collectors.toList());
                List<Product> products = productDao.findAllById(productIds);
                products.forEach(product -> {
                    docObject.put(NeesObjProps.id.name(), product.getId());
                    docObject.put(NeesObjProps.id.name(), product.getName());
                    docObjects.add(docObject);
                });
        }
        return docObjects;
    }

    private void verifyViewRights() {
        LocalAssert.isTrue(neesObjectRightService.hasViewObjectRight(RequestContext.getUserId(), NeesObject.document), "Insufficient right to view Batch");
    }

    private void verifyUpdateRights() {
        LocalAssert.isTrue(neesObjectRightService.hasUpdateObjectRight(RequestContext.getUserId(), NeesObject.document), "Insufficient right to add or update Batch");

    }

    private void verifyFullRights() {
        LocalAssert.isTrue(neesObjectRightService.hasFullObjectRight(RequestContext.getUserId(), NeesObject.document), "Insufficient right to delete Batch");

    }
}
