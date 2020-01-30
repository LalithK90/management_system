package lk.imms.management_system.asset.userManagement.dao;

        import lk.imms.management_system.asset.userManagement.entity.Enum.UserSessionLogStatus;
        import lk.imms.management_system.asset.userManagement.entity.User;
        import lk.imms.management_system.asset.userManagement.entity.UserSessionLog;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository< UserSessionLog, Long > {
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
