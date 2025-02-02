package com.ydova.ahub.repository;

import com.ydova.ahub.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
