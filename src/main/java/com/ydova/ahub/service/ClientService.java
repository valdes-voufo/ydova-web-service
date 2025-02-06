package com.ydova.ahub.service;

import com.ydova.ahub.entity.Client;
import com.ydova.ahub.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {


    private  final ClientRepository clientRepository;


  @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setEmail(clientDetails.getEmail());
            client.setPassword(clientDetails.getPassword());
            client.setLastname(clientDetails.getLastname());
            client.setFirstname(clientDetails.getFirstname());
            return clientRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}


