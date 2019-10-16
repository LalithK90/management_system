package lk.imms.management_system.asset.petition.service;

import lk.imms.management_system.asset.petition.dao.PetitionFilesDao;
import lk.imms.management_system.asset.petition.entity.PetitionFiles;
import lk.imms.management_system.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public PetitionFiles persist(PetitionFiles petitionFiles) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< PetitionFiles > search(PetitionFiles petitionFiles) {
        return null;
    }
}
