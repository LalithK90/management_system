package lk.imms.management_system.asset.OffednerGuardian.dao;

import lk.imms.management_system.asset.OffednerGuardian.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GuardianDao extends JpaRepository< Guardian, Long > {
    Guardian findByNic(String nic);

    List<Guardian> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
