package com.prodnees.core.service.doc;

import com.prodnees.core.domain.doc.NeesFile;

public interface NeesFileService {

    boolean existsByDocId(int docId);

    NeesFile getByDocId(int docId);

    NeesFile save(NeesFile neesFile);

}
