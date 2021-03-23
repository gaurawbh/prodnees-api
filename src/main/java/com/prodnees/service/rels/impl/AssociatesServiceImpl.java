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
    public Optional<Associates> findByAdminIdAndAssociateId(int adminId, int userId) {
        return associatesDao.findByAdminIdAndAssociateId(adminId, userId);
    }

    @Override
    public Optional<Associates> findByAdminIdAndAssociateEmail(int adminId, String associateEmail) {
        return associatesDao.findByAdminIdAndAssociateEmail(adminId, associateEmail);
    }

    @Override
    public boolean existsByAdminIdAndAssociateEmail(int adminId, String associateEmail) {
        return associatesDao.existsByAdminIdAndAssociateEmail(adminId, associateEmail);
    }

    @Override
    public List<Associates> getAllByAdminId(int adminId) {
        return associatesDao.getAllByAdminId(adminId);
    }

    @Override
    public List<Associates> getAllByAssociateId(int userId) {
        return associatesDao.getAllByAssociateId(userId);
    }
}
