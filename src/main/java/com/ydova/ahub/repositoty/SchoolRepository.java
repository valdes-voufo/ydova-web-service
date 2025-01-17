package com.ydova.ahub.repositoty;

import com.ydova.ahub.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository  extends JpaRepository<School,Long> {
    List<School> findSchoolByEmail(String email);
}
