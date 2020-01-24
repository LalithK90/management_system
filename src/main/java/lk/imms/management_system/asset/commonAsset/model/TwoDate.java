package lk.imms.management_system.asset.commonAsset.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwoDate {
    private LocalDate startDate;
    private LocalDate endDate;
}
