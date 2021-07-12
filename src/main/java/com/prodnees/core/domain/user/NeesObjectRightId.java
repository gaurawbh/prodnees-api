package com.prodnees.core.domain.user;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;

public class NeesObjectRightId implements Serializable {

    @Id
    private int userId;
    @Id
    @Enumerated(EnumType.STRING)
    private NeesObject neesObject;

    public NeesObjectRightId() {
    }

    public NeesObjectRightId(int userId, NeesObject neesObject) {
        this.userId = userId;
        this.neesObject = neesObject;
    }

    public int getUserId() {
        return userId;
    }

    public NeesObjectRightId setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public NeesObject getNeesObject() {
        return neesObject;
    }

    public NeesObjectRightId setNeesObject(NeesObject neesObject) {
        this.neesObject = neesObject;
        return this;
    }
}
