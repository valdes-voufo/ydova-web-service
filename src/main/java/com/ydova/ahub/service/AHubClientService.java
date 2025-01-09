package com.ydova.ahub.service;

import com.ydova.ahub.converter.AHubClientConverter;
import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.entity.AHubClient;
import com.ydova.ahub.repositoty.AHubClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AHubClientService {
    private final AHubClientRepository repository;

    @Autowired
    public AHubClientService(AHubClientRepository repository) {
        this.repository = repository;
    }

    // Add a new client
    public AHubClientDto addClient(AHubClientDto dto) {

        dto.setStatus("Student");  // Default status
        return AHubClientConverter.toDto(repository.save( AHubClientConverter.toEntity(dto)));  // Save and return the saved client
    }

    // Remove a client by ID
    public String removeClient(Long id) {
        Optional<AHubClient> client = repository.findById(id);
        if (client.isPresent()) {
            repository.delete(client.get());  // Delete the client
            return "Client with ID " + id + " removed successfully.";
        } else {
            return "Client with ID " + id + " not found.";
        }
    }

    // Update an existing client
    public AHubClientDto updateClient(Long id, AHubClientDto updatedClient) {
        Optional<AHubClient> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return AHubClientConverter.toDto(repository.save( AHubClientConverter.toEntity(updatedClient)));  // Save and return the updated client
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a client by ID
    public AHubClientDto getClient(Long id) {
        return  AHubClientConverter.toDto(repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found.")));
    }

    // Get a list of all clients
    public List<AHubClientDto> getClients() {
        return AHubClientConverter.toDtoList(repository.findAll());  // Return all clients in the repository
    }
}
