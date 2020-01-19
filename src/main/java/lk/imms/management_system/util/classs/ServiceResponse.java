package lk.imms.management_system.util.classs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {

    private String status;
    private T data;

}