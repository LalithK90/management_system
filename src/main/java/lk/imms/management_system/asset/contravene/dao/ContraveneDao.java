package lk.imms.management_system.asset.contravene.dao;

import lk.imms.management_system.asset.contravene.entity.Contravene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContraveneDao extends JpaRepository< Contravene, Long > {
}
