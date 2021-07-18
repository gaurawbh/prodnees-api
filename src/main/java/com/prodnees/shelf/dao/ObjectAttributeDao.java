package com.prodnees.shelf.dao;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.shelf.domain.ObjectAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjectAttributeDao extends JpaRepository<ObjectAttribute, Integer> {

    List<ObjectAttribute> getAllByNeesObject(NeesObject neesObject);
}
