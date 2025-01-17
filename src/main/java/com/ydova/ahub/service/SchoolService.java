package com.ydova.ahub.service;

import com.ydova.ahub.converter.SchoolConverter;
import com.ydova.ahub.dto.SchoolDto;
import com.ydova.ahub.dto.SchoolDto;
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
    public SchoolDto create(SchoolDto dto) {
        if (!repository.findSchoolByEmail(dto.getEmail()).isEmpty()) {
            throw new YdovaGenericException(String.format("Email %s already exists", dto.getEmail()));
        }
        return SchoolConverter.toDto(repository.save(SchoolConverter.toEntity(dto)));  // Save and return the saved school
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
    public SchoolDto update(Long id, SchoolDto updatedClient) {
        Optional<School> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return SchoolConverter.toDto(repository.save(SchoolConverter.toEntity(updatedClient)));  // Save and return the updated school
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a school by ID
    public SchoolDto read(Long id) {
        return SchoolConverter.toDto(repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found.")));
    }

    // Get a list of all schools
    public List<SchoolDto> readAll() {
        return SchoolConverter.toDtoList(repository.findAll());  // Return all schools in the repository
    }
}
