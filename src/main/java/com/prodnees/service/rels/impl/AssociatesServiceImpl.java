package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.AssociatesDao;
import com.prodnees.domain.rels.Associates;
import com.prodnees.service.rels.AssociatesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociatesServiceImpl implements AssociatesService {
    private final AssociatesDao associatesDao;

    public AssociatesServiceImpl(AssociatesDao associatesDao) {
        this.associatesDao = associatesDao;
    }


    @Override
    public Associates save(Associates associates) {
        return associatesDao.save(associates);
    }

    @Override
    public Optional<Associates> findByAdminIdAndUserId(int adminId, int userId) {
        return associatesDao.findByAdminIdAndUserId(adminId, userId);
    }

    @Override
    public List<Associates> getAllByAdminId(int adminId) {
        return associatesDao.getAllByAdminId(adminId);
    }

    @Override
    public List<Associates> getAllByUserId(int userId) {
        return associatesDao.getAllByUserId(userId);
    }
}
