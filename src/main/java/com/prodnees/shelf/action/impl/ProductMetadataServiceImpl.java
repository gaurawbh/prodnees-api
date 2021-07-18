package com.prodnees.shelf.action.impl;

import com.prodnees.core.config.annotations.NeesLabel;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.qc.domain.ValueType;
import com.prodnees.shelf.action.ProductMetadataService;
import com.prodnees.shelf.dao.ObjectAttributeDao;
import com.prodnees.shelf.domain.ObjectAttribute;
import com.prodnees.shelf.domain.Product;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMetadataServiceImpl implements ProductMetadataService {
    private final ObjectAttributeDao objectAttributeDao;

    public ProductMetadataServiceImpl(ObjectAttributeDao objectAttributeDao) {
        this.objectAttributeDao = objectAttributeDao;
    }

    /**
     * todo differentiate between editable fields and non-editable fields
     * @return
     */

    @Override
    public List<ObjectAttribute> getAllProductFields(boolean onlyEditableFields) {
        List<ObjectAttribute> objectAttributes = objectAttributeDao.getAllByNeesObject(NeesObject.product);
        Field[] productFields = Product.class.getDeclaredFields();
        for (Field field : productFields) {
            ObjectAttribute objectAttribute = new ObjectAttribute();
            List<Annotation> annotations = Arrays.stream(field.getAnnotations())
                    .filter(annotation -> annotation instanceof NeesLabel)
                    .collect(Collectors.toList());

            String label = annotations.isEmpty() ? field.getName() : ((NeesLabel) annotations.get(0)).value();
            objectAttribute.setLabel(label)
                    .setNeesObject(NeesObject.product)
                    .setPrivateKey(field.getName())
                    .setValueType(getValueType(field))
                    .setRequired(false)
                    .setSys(true);
            objectAttributes.add(objectAttribute);
        }
        return objectAttributes;
    }

    @Override
    public ValueType getValueType(Field field) {
        String fieldTypeFull = field.getType().getTypeName();
        String[] filedTypeSplit = fieldTypeFull.split("\\.");
        String fieldType = filedTypeSplit[filedTypeSplit.length - 1];

        switch (fieldType) {
            case "int":
            case "double":
            case "Integer":
            case "Double":
            case "Long":
            case "Float":
            case "Short":
                return ValueType.Number;
            case "Boolean":
                return ValueType.Boolean;
            case "LocalDate":
                return ValueType.Date;
            case "LocalTime":
                return ValueType.Time;
            case "LocalDateTime":
                return ValueType.DateTime;
            default:
                return ValueType.String;


        }

    }
}
