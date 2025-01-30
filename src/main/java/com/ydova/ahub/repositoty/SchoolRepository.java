package com.ydova.ahub.repositoty;

import com.ydova.ahub.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository  extends JpaRepository<Email,Long> {
    List<Email> findSchoolByEmail(String email);
}
