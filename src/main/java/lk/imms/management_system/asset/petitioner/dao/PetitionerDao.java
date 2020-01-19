package lk.imms.management_system.asset.petitioner.dao;

import lk.imms.management_system.asset.petitioner.entity.Petitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionerDao extends JpaRepository< Petitioner, Long > {

    @Query( value = "select * from petitioner where petitioner_type=?1", nativeQuery = true )
    Petitioner findByPetitionerType(String petitionerType);
}
