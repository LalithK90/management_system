package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.employee.entity.EmployeeFiles;
import lk.imms.management_system.asset.petition.dao.PetitionFilesDao;
import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionFiles;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PetitionFilesService implements AbstractService< PetitionFiles, Long > {
    private final PetitionFilesDao petitionFilesDao;

    @Autowired
    public PetitionFilesService(PetitionFilesDao petitionFilesDao) {
        this.petitionFilesDao = petitionFilesDao;
    }


    @Override
    public List< PetitionFiles > findAll() {
        return petitionFilesDao.findAll();
    }

    @Override
    public PetitionFiles findById(Long id) {
        return petitionFilesDao.getOne(id);
    }

    @Override
    public PetitionFiles persist(PetitionFiles petitionFiles) {
        return petitionFilesDao.save(petitionFiles);
    }

    @Override
    public boolean delete(Long id) {
        petitionFilesDao.deleteById(id);
        return false;
    }

    @Override
    public List< PetitionFiles > search(PetitionFiles petitionFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< PetitionFiles > petitionFilesExample = Example.of(petitionFiles, matcher);
        return petitionFilesDao.findAll(petitionFilesExample);
    }

    public PetitionFiles findByNewName(String filename) {
        return petitionFilesDao.findByNewName(filename);
    }

    public PetitionFiles findByNewID(String filename) {
        return petitionFilesDao.findByNewId(filename);
    }


    public List<PetitionFiles> findByPetition(Petition petition) {
     return petitionFilesDao.findByPetitionOrderByIdDesc(petition);
    }
}
