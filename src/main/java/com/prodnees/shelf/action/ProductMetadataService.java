package com.prodnees.shelf.action;

import com.prodnees.qc.domain.ValueType;
import com.prodnees.shelf.domain.ObjectAttribute;

import java.lang.reflect.Field;
import java.util.List;

public interface ProductMetadataService {

    List<ObjectAttribute> getAllProductFields(boolean onlyEditableFields);

    ValueType getValueType(Field field);
}
