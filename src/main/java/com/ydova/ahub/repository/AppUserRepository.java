package com.ydova.ahub.repository;

import com.ydova.ahub.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository  extends JpaRepository<AppUser,Long> {
    List<AppUser> findByEmail(String email);

    List<AppUser> findByKey(String key);
}
