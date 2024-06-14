package com.shenll.shelogisticsadminservice.totalRateCard.PackageRate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import com.shenll.shelogisticsadminservice.packageType.PackageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "package_rateCard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageRateCard extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String packageRateId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageType packageId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "from_agent_id")
    private Agent agentId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "to_agent_id")
    private Agent toDestination;

    private String rate;
    private String status;


}
