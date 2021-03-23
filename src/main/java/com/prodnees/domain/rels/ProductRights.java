package com.prodnees.domain.rels;

import com.prodnees.domain.rels.id.ProductRightsId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(ProductRightsId.class)
public class ProductRights implements Serializable {
    @Id
    private int productId;
    @Id
    private int userId;
    private ObjectRightType objectRightType;

    public int getProductId() {
        return productId;
    }

    public ProductRights setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public ProductRights setUserId(int ownerId) {
        this.userId = ownerId;
        return this;
    }

    public ObjectRightType getObjectRightsType() {
        return objectRightType;
    }

    public ProductRights setObjectRightsType(ObjectRightType objectRightType) {
        this.objectRightType = objectRightType;
        return this;
    }
}
