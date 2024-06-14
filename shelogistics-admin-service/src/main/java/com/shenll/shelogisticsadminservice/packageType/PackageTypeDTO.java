package com.shenll.shelogisticsadminservice.packageType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageTypeDTO {

    @Schema(example = "Box/Bundle")
    private String packageName;

    @Schema(example = "Active")
    private String status;
}
