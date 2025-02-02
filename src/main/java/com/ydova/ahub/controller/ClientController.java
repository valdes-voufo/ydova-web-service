package com.ydova.ahub.controller;

import com.ydova.ahub.entity.Client;
import com.ydova.ahub.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Client Operations", description = "Operations for managing clients")
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;


    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @Operation(summary = "Get client by ID", description = "Returns client details for a given client ID")
    @GetMapping("/{id}")
    public Optional<Client> getClientById(
            @Parameter(description = "Client ID", required = true) @PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @Operation(summary = "Create a new client", description = "Creates a new client in the system")
    @PostMapping
    public Client createClient(@Parameter(description = "Client object to be created", required = true) @RequestBody Client client) {
        return clientService.createClient(client);
    }

    @Operation(summary = "Update an existing client", description = "Updates client details for the given client ID")
    @PutMapping("/{id}")
    public Client updateClient(
            @Parameter(description = "Client ID to be updated", required = true) @PathVariable Long id,
            @Parameter(description = "Updated client details", required = true) @RequestBody Client clientDetails) {
        return clientService.updateClient(id, clientDetails);
    }

    @Operation(summary = "Delete a client", description = "Deletes the client with the given ID")
    @DeleteMapping("/{id}")
    public void deleteClient(@Parameter(description = "Client ID to be deleted", required = true) @PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
