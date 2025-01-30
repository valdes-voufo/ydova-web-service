package com.ydova.ahub.service;


import com.ydova.ahub.entity.EMAIL;
import com.ydova.ahub.repositoty.SchoolRepository;

import com.ydova.cv.YdovaException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    private final SchoolRepository repository;

    @Autowired
    public EmailService(SchoolRepository repository) {
        this.repository = repository;
    }

    // Add a new school
    public EMAIL create(EMAIL dto) throws YdovaException {
        if (!repository.findSchoolByEmail(dto.getEmail()).isEmpty()) {
            throw new YdovaException(String.format("Email %s already exists", dto.getEmail()));
        }
        return repository.save(dto);  // Save and return the saved school
    }

    // Remove a school by ID
    public void remove(Long id) {
        Optional<EMAIL> school = repository.findById(id);
        // Delete the school
        school.ifPresent(repository::delete);
    }

    // Update an existing school
    public EMAIL update(Long id, EMAIL updatedClient) {
        Optional<EMAIL> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return repository.save(updatedClient);  // Save and return the updated school
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a school by ID
    public EMAIL read(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found."));
    }

    // Get a list of all schools
    public List<EMAIL> readAll() {
        return repository.findAll();
    }

    public void createAll( List<EMAIL> email) {

        for (EMAIL e : email) {
            if (repository.findSchoolByEmail(e.getEmail()).isEmpty()) {
                repository.save(e);
            }
        }


    }
}
