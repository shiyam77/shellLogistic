package com.shenll.shelogisticsadminservice.agent;


import com.shenll.shelogisticsadminservice.enums.Status;
import com.shenll.shelogisticsadminservice.enums.Type;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    AgentMapper agentMapper;

    @Override
    public CommonResponse createAgent(AgentDTO agentDTO) {
        Agent agent = agentMapper.addAgent(agentDTO);
        agentRepository.save(agent);
        return CommonResponse.builder().code(200).data(agent.getAgentId()).build();
    }

    @Override
    public CommonResponse getAgent() {
        List<Agent> agentList = agentRepository.findByTypeAndStatus(Type.Agent.name(), Status.ACTIVE.name());
        if (!agentList.isEmpty()) {
            return CommonResponse.builder().code(200).message("Success").data(agentList).build();
        } else {
            return CommonResponse.builder().code(404).message("No active record found").build();
        }
    }

    @Override
    public CommonResponse getAgentById(String id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid Agent Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("Success").data(agent).build();

    }

    @Override
    public CommonResponse update(AgentDTO agentDTO, String id) {
        Optional<Agent> optionalAgent = agentRepository.findById(id);
        if (optionalAgent.isPresent()) {
            Agent agent = optionalAgent.get();
            agentMapper.updateAgent(agent, agentDTO);
            agentRepository.save(agent);
            return CommonResponse.builder().code(200).message("Success").data(agent).build();
        } else {
            return CommonResponse.builder().code(201).message("Invalid Id").build();
        }

    }

    @Override
    public CommonResponse deleteAgent(String id) {
        Optional<Agent> optionalAgent = agentRepository.findById(id);
        if (optionalAgent.isPresent()) {
            Agent agent = optionalAgent.get();
//            agent.setStatus(Status.INACTIVE.name());
//            agent.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//            agentRepository.save(agent);
            agentRepository.deleteById(agent.getAgentId());
            return CommonResponse.builder().code(200).message("deleted Successfully").data(optionalAgent.get().getAgentId()).build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid Id").build();
        }
    }

    @Override
    public CommonResponse findAllAgent() {
        List<Agent> list = agentRepository.findAll();
        if (list != null) {
            return CommonResponse.builder().code(200).message("Successfully").data(list).build();

        } else {
            return CommonResponse.builder().code(400).message("error").build();
        }
    }

    @Override
    public CommonResponse getAgentWithFilters(String name, String code, String city, String phone, Long page, Long size) {
        Specification<Agent> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("agentName"), "%" + name + "%"));
            }
            if (code != null && !code.isEmpty()) {
                predicates.add(builder.like(root.get("code"), "%" + code + "%"));
            }
            if (city != null && !city.isEmpty()) {
                predicates.add(builder.like(root.get("city"), "%" + city + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(builder.like(root.get("phone"), "%" + phone + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Agent> agents = agentRepository.findAll(spec, pageable);
        if (agents.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Agent data")
                    .data(agents.getContent())
                    .totalCount(agents.getTotalElements())
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Agents not found")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }
}
