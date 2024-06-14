package com.shenll.shelogisticsadminservice.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>,JpaSpecificationExecutor<Users> {

    Optional<Users> findByKeycloakId(String keycloakId);
}
