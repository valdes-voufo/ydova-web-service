package com.ydova.ahub.service;


import com.ydova.Log;
import com.ydova.ahub.entity.AHubClient;
import com.ydova.ahub.repository.AHubClientRepository;
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
    public AHubClient create(AHubClient dto) {
        dto.setStatus("Student");
        Log.info(dto.toString(),this.getClass());
        return repository.save( dto);

    }

    // Remove a client by ID
    public String remove(Long id) {
        Optional<AHubClient> client = repository.findById(id);
        if (client.isPresent()) {
            repository.delete(client.get());  // Delete the client
            return "Client with ID " + id + " removed successfully.";
        } else {
            return "Client with ID " + id + " not found.";
        }
    }

    // Update an existing client
    public AHubClient update(Long id, AHubClient updatedClient) {
        Optional<AHubClient> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return repository.save( updatedClient);  // Save and return the updated client
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a client by ID
    public AHubClient read(Long id) {
        return  repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found."));
    }

    // Get a list of all clients
    public List<AHubClient> readAll() {
        return repository.findAll();  // Return all clients in the repository
    }
}
