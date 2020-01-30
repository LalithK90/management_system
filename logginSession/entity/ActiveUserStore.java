package lk.imms.management_system.util.logginSession.entity;

import lk.imms.management_system.util.audit.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ActiveUserStore extends AuditEntity {

    public List<String> users;

    public ActiveUserStore() {
        users = new ArrayList<String>();
    }

}