package com.ydova.ahub.controller;

import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.repositoty.AHubClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
@RestController
public class AHubController {
    private final AHubClientRepository repository;

    @Autowired
    public AHubController(AHubClientRepository repository) {
        this.repository = repository;
    }

    // Add a new client
    @PostMapping("/a-hub/add-client")
    public AHubClientDto addClient(@RequestBody AHubClientDto AHubClientDto) {
        AHubClientDto.setStatus("Student");  // Default status
        return repository.save(AHubClientDto);  // Save and return the saved client
    }

    // Remove a client by ID
    @DeleteMapping("/a-hub/remove-client/{id}")
    public String removeClient(@PathVariable Long id) {
        Optional<AHubClientDto> client = repository.findById(id);
        if (client.isPresent()) {
            repository.delete(client.get());  // Delete the client
            return "Client with ID " + id + " removed successfully.";
        } else {
            return "Client with ID " + id + " not found.";
        }
    }

    // Update an existing client
    @PutMapping("/a-hub/update-client/{id}")
    public AHubClientDto updateClient(@PathVariable Long id, @RequestBody AHubClientDto updatedClient) {
        Optional<AHubClientDto> existingClient = repository.findById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);  // Ensure the ID is set for update
            return repository.save(updatedClient);  // Save and return the updated client
        } else {
            throw new RuntimeException("Client with ID " + id + " not found.");
        }
    }

    // Get a client by ID
    @GetMapping("/a-hub/get-client/{id}")
    public AHubClientDto getClient(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Client not found."));
    }

    // Get a list of all clients
    @GetMapping("/a-hub/get-clients")
    public List<AHubClientDto> getClients() {
        return repository.findAll();  // Return all clients in the repository
    }
}*/
