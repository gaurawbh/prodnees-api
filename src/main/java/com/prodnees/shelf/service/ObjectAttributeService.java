package com.prodnees.shelf.service;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.dto.ObjectAttributeDto;
import com.prodnees.shelf.domain.ObjectAttribute;

import java.util.List;

public interface ObjectAttributeService {

    ObjectAttribute addObjectAttribute(ObjectAttributeDto dto);

    ObjectAttribute update(ObjectAttributeDto dto);

    List<ObjectAttribute> getAllByNeesObject(NeesObject neesObject);
}
