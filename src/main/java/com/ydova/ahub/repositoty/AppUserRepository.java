package com.ydova.ahub.repositoty;

import com.ydova.ahub.entity.AppUser;
import com.ydova.ahub.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository  extends JpaRepository<AppUser,String> {
    List<AppUser> findByEmail(String email);

    List<AppUser> findByKey(String key);
}
