package lk.imms.management_system.asset.petition.dao;

import lk.imms.management_system.asset.petition.entity.PetitionerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionerDetailDao extends JpaRepository< PetitionerDetail, Long > {
}
