package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.PetitionFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionFilesDao extends JpaRepository< PetitionFiles, Long > {
}
