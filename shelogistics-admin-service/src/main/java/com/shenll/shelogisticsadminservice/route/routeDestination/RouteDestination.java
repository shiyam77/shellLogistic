package com.shenll.shelogisticsadminservice.route.routeDestination;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import com.shenll.shelogisticsadminservice.route.Route;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "db_route_destination")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDestination extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "start_destination_agent_id")
    private Agent startDestination;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "end_destination_agent_id")
    private Agent endDestination;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "UTC")
    private String startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "UTC")
    private String endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_timing")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "UTC")
    private String startTiming;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_timing")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "UTC")
    private String endTiming;

    private String status;


}
