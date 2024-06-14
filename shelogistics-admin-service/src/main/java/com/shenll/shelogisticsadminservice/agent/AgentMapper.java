package com.shenll.shelogisticsadminservice.agent;


import com.shenll.shelogisticsadminservice.enums.BookingStatus;
import com.shenll.shelogisticsadminservice.enums.Status;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
@Service
public class AgentMapper {
   public Agent addAgent(AgentDTO agentDTO) {
        Agent agent = new Agent().builder().agentName(agentDTO.getAgentName())
                .type(agentDTO.getType())
                .agentType(agentDTO.getAgentType())
                .parentId(agentDTO.getParentId())
                .code(agentDTO.getCode())
                .oldCode(agentDTO.getOldCode())
                .permanentAddress(agentDTO.getPermanentAddress())
                .currentAddress(agentDTO.getCurrentAddress())
                .email(agentDTO.getEmail())
                .phone(agentDTO.getPhone())
                .mobile(agentDTO.getMobile())
                .city(agentDTO.getCity())
                .state(agentDTO.getState())
                .pincode(agentDTO.getPincode())
                .status(Status.ACTIVE.name())
                .bookingStatus(BookingStatus.ACTIVE.name())
                .build();
        return agent;
    }

    public Agent updateAgent(Agent agent,AgentDTO agentDTO) {
        agent.setAgentName(agentDTO.getAgentName() != null ? agentDTO.getAgentName() : agent.getAgentName());
        agent.setType(agentDTO.getType() != null ? agentDTO.getType() : agent.getType());
        agent.setAgentType(agentDTO.getAgentType() != null ? agentDTO.getAgentType() : agent.getAgentType());
        agent.setParentId(agentDTO.getParentId() != null ? agentDTO.getParentId() : agent.getParentId());
        agent.setCode(agentDTO.getCode() != null ? agentDTO.getCode() : agent.getCode());
        agent.setOldCode(agentDTO.getOldCode() != null ? agentDTO.getOldCode() : agent.getOldCode());
        agent.setPermanentAddress(agentDTO.getPermanentAddress() != null ? agentDTO.getPermanentAddress() : agent.getPermanentAddress());
        agent.setCurrentAddress(agentDTO.getCurrentAddress() != null ? agentDTO.getCurrentAddress() : agent.getCurrentAddress());
        agent.setEmail(agentDTO.getEmail() != null ? agentDTO.getEmail() : agent.getEmail());
        agent.setPhone(agentDTO.getPhone() != null ? agentDTO.getPhone() : agent.getPhone());
        agent.setMobile(agentDTO.getMobile() != null ? agentDTO.getMobile() : agent.getMobile());
        agent.setCity(agentDTO.getCity() != null ? agentDTO.getCity() : agent.getCity());
        agent.setState(agentDTO.getState() != null ? agentDTO.getState() : agent.getState());
        agent.setPincode(agentDTO.getPincode() != null ? agentDTO.getPincode() : agent.getPincode());
        agent.setStatus(agentDTO.getStatus() != null ? agentDTO.getStatus() : agent.getStatus());
        agent.setBookingStatus(agentDTO.getBookingStatus() != null ? agentDTO.getBookingStatus() : agent.getBookingStatus());
        return agent;
    }
}
