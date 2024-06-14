package com.shenll.shelogisticsadminservice.commission;

import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.constant.Constant;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommissionServiceImpl implements CommissionService {
    @Autowired
    CommissionMapper commissionMapper;
    @Autowired
    CommissionRepository commissionRepository;
    @Autowired
    AgentRepository agentRepository;

    @Override
    public CommonResponse createCommission(CommissionDTO commissionDTO) {
        if (commissionDTO.getCommissionId() == null) {
            Agent agent = agentRepository.findById(commissionDTO.getAgentId())
                    .orElseThrow(() -> new AppException("Agent Id is Invalid", HttpStatus.BAD_REQUEST));

            System.out.println("add data");
            Commission commission = commissionMapper.addCommission(commissionDTO, agent);
            commissionRepository.save(commission);
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully Added Commission Data")
                    .data(commission.getCommissionId())
                    .build();
        } else {
            System.out.println("update data");
            Commission commissionId = commissionRepository.findById(commissionDTO.getCommissionId())
                    .orElseThrow(() -> new AppException("Commission Id is Invalid", HttpStatus.BAD_REQUEST));

            Agent updateAgentId = agentRepository.findById(commissionDTO.getAgentId() != null ? commissionDTO.getAgentId() : commissionId.getAgentId().getAgentId())
                    .orElseThrow(() -> new AppException("Agent Id is Invalid", HttpStatus.BAD_REQUEST));

            Commission commission = commissionMapper.updateCommissionById(commissionId, commissionDTO, updateAgentId);
            commissionRepository.save(commission);
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully updated Commission data")
                    .data(commission)
                    .build();
        }
    }

    @Override
    public CommonResponse findAllCommissionData(Long page, Long size) {
        Specification<Commission> spec1 = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = builder.in(root.get("status"))
                    .value(Constant.ACTIVE_STATUS)
                    .value(Constant.INACTIVE_STATUS);
            predicates.add(statusPredicate);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Commission> commissions = commissionRepository.findAll(spec1, pageable);
        if (commissions.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Commission data")
                    .data(commissions.getContent())
                    .totalCount(commissions.getTotalElements())
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("Commission data is empty")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }

    @Override
    public CommonResponse findById(String id) {
        Commission commission = commissionRepository.findById(id)
                .orElseThrow(() -> new AppException("Commission Id is Invalid", HttpStatus.BAD_REQUEST));
        return CommonResponse.builder()
                .code(200)
                .message("Successfully fetched Commission data")
                .data(commission)
                .build();
    }

    @Override
    public CommonResponse deleteById(String id) {
        Commission commission = commissionRepository.findById(id)
                .orElseThrow(() -> new AppException("Commission Id is Invalid", HttpStatus.BAD_REQUEST));
        commission.setStatus("DELETED");
        commission.setUpdatedAt(LocalDateTime.now());
        commissionRepository.save(commission);
        return CommonResponse.builder()
                .code(200)
                .message("Successfully Deleted Commission data")
                .data(commission)
                .build();
    }
}
