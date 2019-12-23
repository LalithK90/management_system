package lk.imms.management_system.asset.minutePetition.service;


import lk.imms.management_system.asset.commonAsset.entity.FileInfo;
import lk.imms.management_system.asset.minutePetition.dao.MinutePetitionDao;
import lk.imms.management_system.asset.minutePetition.entity.MinutePetition;
import lk.imms.management_system.asset.petition.controller.PetitionController;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"minutePetition"} ) // tells Spring where to store cache for this class
public class MinutePetitionService implements AbstractService< MinutePetition, Long > {
    private final MinutePetitionDao minutePetitionDao;
    private final MinutePetitionFilesService minutePetitionFilesService;

    @Autowired
    public MinutePetitionService(MinutePetitionDao minutePetitionDao,
                                 MinutePetitionFilesService minutePetitionFilesService) {
        this.minutePetitionDao = minutePetitionDao;
        this.minutePetitionFilesService = minutePetitionFilesService;
    }

    @Override
    @Cacheable
    public List< MinutePetition > findAll() {
        return minutePetitionDao.findAll();
    }

    @Override
    public MinutePetition findById(Long id) {
        return minutePetitionDao.getOne(id);
    }

    @Override
    @CachePut
    public MinutePetition persist(MinutePetition minutePetition) {
        return minutePetitionDao.save(minutePetition);
    }

    @Override
    public boolean delete(Long id) {
        // there is no possibility to delete any minutePetition
        return false;
    }

    @Override
    public List< MinutePetition > search(MinutePetition minutePetition) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< MinutePetition > minuteExample = Example.of(minutePetition, matcher);
        return minutePetitionDao.findAll(minuteExample);
    }

    public List< MinutePetition > findByPetition(Petition petition) {
        List< MinutePetition > minutePetitions = new ArrayList<>();
        //taken all minute petition according to the petition and
        // taken minute petition files belong to minute petition
        for ( MinutePetition minutePetition : minutePetitionDao.findByPetition(petition) ) {
            List< FileInfo > fileInfos = minutePetitionFilesService.findByMinutePetition(minutePetition)
                    .stream()
                    .map(PetitionFiles -> {
                        String filename = PetitionFiles.getName();
                        String url = MvcUriComponentsBuilder
                                .fromMethodName(PetitionController.class, "downloadFile", PetitionFiles.getNewId())
                                .build()
                                .toString();
                        return new FileInfo(filename, PetitionFiles.getCreatedAt(), url);
                    })
                    .collect(Collectors.toList());
            minutePetition.setFileInfos(fileInfos);
            minutePetitions.add(minutePetition);
        }

        return minutePetitions;
    }
}
