package lk.imms.management_system.asset.minutePetition.service;

import lk.imms.management_system.asset.minutePetition.dao.MinutePetitionFilesDao;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetitionFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = "minutePetitionFiles" )
public class MinutePetitionFilesService {
    private final MinutePetitionFilesDao minutePetitionFilesDao;

    @Autowired
    public MinutePetitionFilesService(MinutePetitionFilesDao minutePetitionFilesDao) {
        this.minutePetitionFilesDao = minutePetitionFilesDao;
    }

    @Cacheable
    public MinutePetitionFiles findByName(String filename) {
        return minutePetitionFilesDao.findByName(filename);
    }

    @Caching( evict = {@CacheEvict( value = "minutePetitionFiles", allEntries = true )},
            put = {@CachePut( value = "minutePetitionFiles", key = "#minutePetitionFiles.id" )} )
    public void persist(List< MinutePetitionFiles > storedFile) {
        minutePetitionFilesDao.saveAll(storedFile);
    }

    @Cacheable
    public List< MinutePetitionFiles > search(MinutePetitionFiles minutePetitionFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< MinutePetitionFiles > minutePetitionFilesExample = Example.of(minutePetitionFiles, matcher);
        return minutePetitionFilesDao.findAll(minutePetitionFilesExample);
    }

    @Cacheable
    public MinutePetitionFiles findById(Long id) {
        return minutePetitionFilesDao.getOne(id);
    }

    public MinutePetitionFiles findByNewName(String filename) {
        return minutePetitionFilesDao.findByNewName(filename);
    }

    @Cacheable
    public MinutePetitionFiles findByNewID(String filename) {
        return minutePetitionFilesDao.findByNewId(filename);
    }

    @Cacheable
    public List< MinutePetitionFiles > findByMinutePetition(MinutePetition minutePetition) {
        //return employeeFilesDao.findByEmployee(employee);
        return minutePetitionFilesDao.findByMinutePetitionOrderByIdDesc(minutePetition);
    }
}
