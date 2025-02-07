package com.ydova.ahub.repository;

import com.ydova.ahub.entity.EmailApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailApplicationRepository extends JpaRepository<EmailApplication, Long> {

    EmailApplication getAllById(Long id);
}
