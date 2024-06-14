package com.shenll.shelogisticsadminservice.packageType;

import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageType extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String packageId;

    private String packageName;
    
    private String status;

}
