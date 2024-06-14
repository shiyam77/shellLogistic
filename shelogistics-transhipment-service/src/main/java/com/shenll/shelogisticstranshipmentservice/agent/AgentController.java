package com.shenll.shelogisticstranshipmentservice.agent;


import com.shenll.shelogisticstranshipmentservice.responseDTO.CommonResponse;
import com.shenll.shelogisticstranshipmentservice.webclient.Agent.AgentServiceClient;
import com.shenll.shelogisticstranshipmentservice.webclient.Agent.AgentVehDTO;
import com.shenll.shelogisticstranshipmentservice.webclient.Customer.CustomerServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Transhipment")
public class AgentController {

    @Autowired
    AgentServiceClient agentServiceClient;

    @Autowired
    CustomerServiceClient customerServiceClient;

    @GetMapping("/getAllAgent")
    public CommonResponse getAll() {
        List<AgentVehDTO> agentVehDTO = agentServiceClient.getAgent();
        return CommonResponse.builder().code(200).data(agentVehDTO).build();
    }
}
