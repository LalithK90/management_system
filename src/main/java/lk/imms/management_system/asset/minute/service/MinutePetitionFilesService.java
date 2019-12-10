package lk.imms.management_system.asset.minute.service;

import lk.imms.management_system.asset.minute.dao.MinutePetitionFilesDao;
import lk.imms.management_system.asset.minute.entity.MinutePetitionFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinutePetitionFilesService {
    private final MinutePetitionFilesDao minutePetitionFilesDao;

    @Autowired
    public MinutePetitionFilesService(MinutePetitionFilesDao minutePetitionFilesDao) {
        this.minutePetitionFilesDao = minutePetitionFilesDao;
    }

    public MinutePetitionFiles findByName(String filename) {
        return   minutePetitionFilesDao.findByName(filename);
    }

    public void persist(List< MinutePetitionFiles > storedFile) {
        minutePetitionFilesDao.saveAll(storedFile);
    }

    public List< MinutePetitionFiles > search(MinutePetitionFiles minutePetitionFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< MinutePetitionFiles > minutePetitionFilesExample = Example.of(minutePetitionFiles, matcher);
        return minutePetitionFilesDao.findAll(minutePetitionFilesExample);
    }

    public MinutePetitionFiles findById(Long id) {
        return minutePetitionFilesDao.getOne(id);
    }

    public MinutePetitionFiles findByNewName(String filename) {
        return minutePetitionFilesDao.findByNewName(filename);
    }

    public MinutePetitionFiles findByNewID(String filename) {
        return minutePetitionFilesDao.findByNewId(filename);
    }

}
