package com.prodnees.core.domain.doc;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NeesFile {
    @Id
    private int docId;
    private byte[] file;

    public int getDocId() {
        return docId;
    }

    public NeesFile setDocId(int docId) {
        this.docId = docId;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public NeesFile setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
