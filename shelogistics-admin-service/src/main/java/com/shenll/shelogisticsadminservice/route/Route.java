package com.shenll.shelogisticsadminservice.route;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "db_route")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String routeType;
    private String routeName;
    private String routeCode;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "from_agent_id")
    private Agent fromDestination;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "to_agent_id")
    private Agent toDestination;

    private String status;

}
