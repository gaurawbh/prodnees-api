package com.prodnees.core.service.doc.impl;

import com.prodnees.core.dao.doc.NeesFileDao;
import com.prodnees.core.domain.doc.NeesFile;
import com.prodnees.core.service.doc.NeesFileService;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NeesFileServiceImpl implements NeesFileService {

    private final NeesFileDao neesFileDao;

    public NeesFileServiceImpl(NeesFileDao neesFileDao) {
        this.neesFileDao = neesFileDao;
    }

    @Override
    public boolean existsByDocId(int docId) {
        return neesFileDao.existsById(docId);
    }

    @Override
    public NeesFile getByDocId(int docId) {
        return neesFileDao.findById(docId)
                .orElseThrow(() -> new NeesNotFoundException(String.format("File with docId: %d not found", docId)));
    }

    @Override
    public NeesFile save(NeesFile neesFile) {
        return neesFileDao.save(neesFile);
    }
}
