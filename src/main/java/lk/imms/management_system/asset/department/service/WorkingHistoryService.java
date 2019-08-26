package lk.imms.management_system.asset.department.service;

import lk.imms.management_system.asset.department.dao.WorkingHistoryDao;
import lk.imms.management_system.asset.department.entity.WorkingHistory;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingHistoryService implements AbstractService< WorkingHistory, Long > {
 private final WorkingHistoryDao workingHistoryDao;
@Autowired
    public WorkingHistoryService(WorkingHistoryDao workingHistoryDao) {
        this.workingHistoryDao = workingHistoryDao;
    }

    @Override
    public List< WorkingHistory > findAll() {
        return workingHistoryDao.findAll();
    }

    @Override
    public WorkingHistory findById(Long id) {
        return null;
    }

    @Override
    public WorkingHistory persist(WorkingHistory workingHistory) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< WorkingHistory > search(WorkingHistory workingHistory) {
        return null;
    }
}
