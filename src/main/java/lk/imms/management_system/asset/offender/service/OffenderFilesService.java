package lk.imms.management_system.asset.offender.service;

import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.offender.controller.OffenderController;
import lk.imms.management_system.asset.offender.dao.OffenderFilesDao;
import lk.imms.management_system.asset.offender.entity.Offender;
import lk.imms.management_system.asset.offender.entity.OffenderFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"offenderFiles"} ) // tells Spring where to store cache for this class
public class OffenderFilesService {
    private final OffenderFilesDao offenderFilesDao;

    @Autowired
    public OffenderFilesService(OffenderFilesDao offenderFilesDao) {
        this.offenderFilesDao = offenderFilesDao;
    }

    @Cacheable
    public List< OffenderFiles > findByOffender(Offender offender) {
        //return employeeFilesDao.findByEmployee(employee);
        return offenderFilesDao.findByOffenderOrderByIdDesc(offender);
    }

    @Cacheable
    public OffenderFiles findByNameAndOffender(String filename, Offender offender) {
        return offenderFilesDao.findByNameAndOffender(filename, offender);
    }

    public List< OffenderFiles > search(OffenderFiles offenderFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< OffenderFiles > offenderFilesExample = Example.of(offenderFiles, matcher);
        return offenderFilesDao.findAll(offenderFilesExample);
    }

    @Cacheable
    public OffenderFiles findById(Long id) {
        return offenderFilesDao.getOne(id);
    }

    @Cacheable
    public OffenderFiles findByNewID(String filename) {
        return offenderFilesDao.findByNewId(filename);
    }

    //To get files from the database
    @Cacheable
    public List< FileInfo > offenderFileDownloadLinks(Offender offender) {
        return offenderFilesDao.findByOffenderOrderByIdDesc(offender)
                .stream()
                .map(OffenderFiles -> {
                    String filename = OffenderFiles.getName();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(OffenderController.class, "downloadFile", OffenderFiles.getNewId())
                            .build()
                            .toString();
                    return new FileInfo(filename, OffenderFiles.getCreatedAt(), url);
                })
                .collect(Collectors.toList());
    }

    @Caching( evict = {@CacheEvict( value = "offenderFiles", allEntries = true )},
            put = {@CachePut( value = "offenderFiles", key = "#offenderFiles.id" )} )
    public void save(OffenderFiles offenderFiles) {
        offenderFilesDao.save(offenderFiles);
    }
}
