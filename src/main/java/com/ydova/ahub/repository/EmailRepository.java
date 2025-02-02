package com.ydova.ahub.repository;

import com.ydova.ahub.entity.Email;
import com.ydova.ahub.entity.EmailGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email,Long> {
    List<Email> findByEmail(String email);

    List<Email> findByGroupName(String group);
}
