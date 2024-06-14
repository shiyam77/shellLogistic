package com.shenll.shelogisticsadminservice.unit;

import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unit")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Unit extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String unitId;
    private String unitName;
    private String status;

}
