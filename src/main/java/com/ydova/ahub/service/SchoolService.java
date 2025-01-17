package com.ydova.ahub.service;


import com.ydova.ahub.entity.School;
import com.ydova.ahub.repositoty.SchoolRepository;
import com.ydova.mail.YdovaGenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    private final SchoolRepository repository;

    @Autowired
    public SchoolService(SchoolRepository repository) {
        this.repository = repository;
    }

    // Add a new school
    public School create(School dto) {
        if (!repository.findSchoolByEmail(dto.getEmail()).isEmpty()) {
            throw new YdovaGenericException(String.format("Email %s already exists", dto.getEmail()));
        }
        return repository.save(dto);  // Save and return the saved school
    }

    // Remove a school by ID
    public String remove(Long id) {
        Optional<School> school = repository.findById(id);
        if (school.isPresent()) {
            repository.delete(school.get());  // Delete the school
            return "Client with ID " + id + " removed successfully.";
        } else {
            return "Client with ID " + id + " not found.";
        }
    }

    // Update an existing school
    public School update(Long id, School updatedClient) {
        Optional<School> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return repository.save(updatedClient);  // Save and return the updated school
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a school by ID
    public School read(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found."));
    }

    // Get a list of all schools
    public List<School> readAll() {
        return repository.findAll();
    }
}
