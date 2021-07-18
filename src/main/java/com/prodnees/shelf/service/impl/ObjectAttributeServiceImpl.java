package com.prodnees.shelf.service.impl;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.dto.ObjectAttributeDto;
import com.prodnees.core.util.LocalStringUtils;
import com.prodnees.shelf.dao.ObjectAttributeDao;
import com.prodnees.shelf.domain.ObjectAttribute;
import com.prodnees.shelf.service.ObjectAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectAttributeServiceImpl implements ObjectAttributeService {
    private final ObjectAttributeDao objectAttributeDao;

    public ObjectAttributeServiceImpl(ObjectAttributeDao objectAttributeDao) {
        this.objectAttributeDao = objectAttributeDao;
    }

    @Override
    public ObjectAttribute addObjectAttribute(ObjectAttributeDto dto) {
        ObjectAttribute objectAttribute = new ObjectAttribute()
                .setNeesObject(dto.getNeesObject())
                .setPrivateKey(LocalStringUtils.toLowerCamelCase(dto.getLabel()))
                .setLabel(dto.getLabel())
                .setHelpContent(dto.getHelpContent())
                .setValueType(dto.getValueType())
                .setRequired(dto.getRequired())
                .setSys(false);
        return objectAttributeDao.save(objectAttribute);
    }

    @Override
    public ObjectAttribute update(ObjectAttributeDto dto) {
        return null;
    }

    @Override
    public List<ObjectAttribute> getAllByNeesObject(NeesObject neesObject) {
        return objectAttributeDao.getAllByNeesObject(neesObject);
    }
}
