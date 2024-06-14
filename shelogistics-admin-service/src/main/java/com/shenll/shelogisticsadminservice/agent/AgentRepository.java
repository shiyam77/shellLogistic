package com.shenll.shelogisticsadminservice.agent;

import com.shenll.shelogisticsadminservice.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent,String>, JpaSpecificationExecutor<Agent> {
    List<Agent> findByTypeAndStatus(String type, String status);
}

