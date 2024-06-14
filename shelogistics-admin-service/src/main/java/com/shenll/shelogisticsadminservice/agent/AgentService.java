package com.shenll.shelogisticsadminservice.agent;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface AgentService {

    CommonResponse createAgent(AgentDTO agent);

    CommonResponse getAgent();

    CommonResponse getAgentById(String id);


    CommonResponse update(AgentDTO agentDTO,String id);

    CommonResponse deleteAgent(String id);

    CommonResponse findAllAgent();

    CommonResponse getAgentWithFilters(String name, String code, String city, String phone, Long page, Long size);
}
