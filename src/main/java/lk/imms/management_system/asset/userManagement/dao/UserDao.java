package lk.imms.management_system.asset.userManagement.dao;


import lk.imms.management_system.asset.employee.entity.Employee;
import lk.imms.management_system.asset.userManagement.entity.Role;
import lk.imms.management_system.asset.userManagement.entity.User;
import lk.imms.management_system.asset.workingPlace.entity.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository< User, Long > {

    @Query( value = "select id from User where employee_id=?1", nativeQuery = true )
    Long findByEmployeeId(@Param( "employee_id" ) Long id);

    @Query( "select id from User where username=?1" )
    Long findUserIdByUserName(String userName);

    User findByUsername(String name);

    User findByEmployee(Employee employee);

    List<User> findByWorkingPlaces(WorkingPlace workingPlace);

    List<User> findByWorkingPlacesAndRoles(WorkingPlace workingPlace, Role role);
}
