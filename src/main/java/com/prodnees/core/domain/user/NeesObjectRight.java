package com.prodnees.core.domain.user;

import com.prodnees.core.domain.enums.ObjectRight;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NeesObjectRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    @Enumerated(EnumType.STRING)
    private NeesObject neesObject;
    @Enumerated(EnumType.STRING)
    private ObjectRight objectRight;

    public int getId() {
        return id;
    }

    public NeesObjectRight setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public NeesObjectRight setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public NeesObject getNeesObject() {
        return neesObject;
    }

    public NeesObjectRight setNeesObject(NeesObject neesObject) {
        this.neesObject = neesObject;
        return this;
    }

    public ObjectRight getObjectRight() {
        return objectRight;
    }

    public NeesObjectRight setObjectRight(ObjectRight objectRight) {
        this.objectRight = objectRight;
        return this;
    }
}
