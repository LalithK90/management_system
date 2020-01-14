package lk.imms.management_system.asset.commonAsset.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private Long id;

    private String message;

    private List< MultipartFile > files = new ArrayList<>();
}
