package com.ydova.ahub.repositoty;

import com.ydova.ahub.entity.EMAIL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository  extends JpaRepository<EMAIL,Long> {
    List<EMAIL> findSchoolByEmail(String email);
}
