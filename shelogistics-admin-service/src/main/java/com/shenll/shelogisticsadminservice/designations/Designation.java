package com.shenll.shelogisticsadminservice.designations;

import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "designations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Designation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String designationId;
    private String designation;
    private  String status;

}
