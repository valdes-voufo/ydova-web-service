package com.ydova.ahub.repository;

import com.ydova.ahub.entity.ApplicationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationJobRepository extends JpaRepository<ApplicationJob, Long> {
    List<ApplicationJob> findAll();
}
