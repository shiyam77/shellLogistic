package com.shenll.shelogisticstranshipmentservice.webclient.Agent;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class AgentListResponse {
    private List<AgentVehDTO> data;
}
