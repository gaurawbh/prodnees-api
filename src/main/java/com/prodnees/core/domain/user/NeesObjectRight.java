package com.prodnees.core.domain.user;

import com.prodnees.core.domain.enums.ObjectRight;
import org.springframework.data.annotation.Reference;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(NeesObjectRightId.class)
public class NeesObjectRight {
    @Id
    @Reference(to = UserAttributes.class)
    private int userId;
    @Id
    @Enumerated(EnumType.STRING)
    private NeesObject neesObject;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "objectRight is a required field")
    private ObjectRight objectRight;


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
