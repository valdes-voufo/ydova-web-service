package com.ydova.ahub.service;


import com.ydova.ahub.entity.Email;
import com.ydova.ahub.repository.EmailRepository;

import com.ydova.cv.YdovaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    private final EmailRepository repository;

    @Autowired
    public EmailService(EmailRepository repository) {
        this.repository = repository;
    }

    // Add a new school
    public Email create(Email dto) throws YdovaException {
        if (!repository.findByEmail(dto.getEmail()).isEmpty()) {
            throw new YdovaException(String.format("Email %s already exists", dto.getEmail()));
        }
        return repository.save(dto);  // Save and return the saved school
    }

    // Remove a school by ID
    public void remove(Long id) {
        Optional<Email> school = repository.findById(id);
        // Delete the school
        school.ifPresent(repository::delete);
    }

    // Update an existing school
    public Email update(Long id, Email updatedClient) {
        Optional<Email> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return repository.save(updatedClient);  // Save and return the updated school
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a school by ID
    public Email read(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found."));
    }

    // Get a list of all schools
    public List<Email> readAll() {
        return repository.findAll();
    }

    public void createAll( List<Email> email) {

        for (Email e : email) {
            if (repository.findByEmail(e.getEmail()).isEmpty()) {
                repository.save(e);
            }
        }


    }
}
