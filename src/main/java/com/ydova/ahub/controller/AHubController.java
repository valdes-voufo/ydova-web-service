package com.ydova.ahub.controller;


import com.ydova.ahub.entity.AHubClient;
import com.ydova.ahub.service.AHubClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AHubController {
    private final AHubClientService service;


    public AHubController(AHubClientService service) {
        this.service = service;
    }

    @PostMapping
    public com.ydova.ahub.entity.AHubClient addClient(@RequestBody AHubClient AHubClient) {
        AHubClient.setStatus("Student");
        return service.create(AHubClient);
    }


    @DeleteMapping("/{id}")
    public String removeClient(@PathVariable Long id) {
        return service.remove(id);

    }


    @PutMapping("/{id}")
    public AHubClient updateClient(@PathVariable Long id, @RequestBody AHubClient updatedClient) {
        return service.create(updatedClient);
    }


    @GetMapping("/{id}")
    public AHubClient getClient(@PathVariable Long id) {
        return service.read(id);
    }


    @GetMapping
    public List<AHubClient> getClients() {
        return service.readAll();
    }
}
