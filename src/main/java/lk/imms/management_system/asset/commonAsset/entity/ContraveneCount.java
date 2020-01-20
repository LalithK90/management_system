package lk.imms.management_system.asset.commonAsset.entity;

import lk.imms.management_system.asset.petitionAddOffender.entity.PetitionOffender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContraveneCount {
    private String name;
    private Long count;
}
