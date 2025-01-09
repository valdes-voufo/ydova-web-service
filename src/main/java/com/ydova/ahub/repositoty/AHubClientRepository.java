package com.ydova.ahub.repositoty;

import com.ydova.ahub.entity.AHubClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AHubClientRepository extends JpaRepository<AHubClient, Long> {
}
