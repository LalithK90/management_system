package lk.imms.management_system.asset.commonAsset.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private String filename;
    private LocalDateTime createAt;
    private String url;
}

