package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.Petition;
import lk.imms.management_system.asset.petition.entity.PetitionFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PetitionFilesDao extends JpaRepository< PetitionFiles, Long > {
    PetitionFiles findByNewName(String filename);

    PetitionFiles findByNewId(String filename);

    List<PetitionFiles> findByPetitionOrderByIdDesc(Petition petition);
}
