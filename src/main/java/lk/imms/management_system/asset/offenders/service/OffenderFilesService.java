package lk.imms.management_system.asset.offenders.service;

import lk.imms.management_system.asset.offenders.dao.OffenderFilesDao;
import lk.imms.management_system.asset.offenders.entity.Offender;
import lk.imms.management_system.asset.offenders.entity.OffenderFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenderFilesService {
    private final OffenderFilesDao offenderFilesDao;

    @Autowired
    public OffenderFilesService(OffenderFilesDao offenderFilesDao) {
        this.offenderFilesDao = offenderFilesDao;
    }


    public List< OffenderFiles > findByOffender(Offender offender) {
        //return employeeFilesDao.findByEmployee(employee);
        return offenderFilesDao.findByOffenderOrderByIdDesc(offender);
    }

    public OffenderFiles findByName(String filename) {
        return offenderFilesDao.findByName(filename);
    }

    public void persist(List< OffenderFiles > storedFile) {
        offenderFilesDao.saveAll(storedFile);
    }

    public List< OffenderFiles > search(OffenderFiles offenderFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< OffenderFiles > offenderFilesExample = Example.of(offenderFiles, matcher);
        return offenderFilesDao.findAll(offenderFilesExample);
    }

    public OffenderFiles findById(Long id) {
        return offenderFilesDao.getOne(id);
    }

    public OffenderFiles findByNewName(String filename) {
        return offenderFilesDao.findByNewName(filename);
    }

    public OffenderFiles findByNewID(String filename) {
        return offenderFilesDao.findByNewId(filename);
    }
}
