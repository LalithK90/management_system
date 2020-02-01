package lk.imms.management_system.asset.message.dao;

import lk.imms.management_system.asset.message.entity.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailMessageDao extends JpaRepository< EmailMessage, Long > {
}
