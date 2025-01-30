package com.ydova.ahub.service;

import com.ydova.ahub.entity.EmailGroup;

import com.ydova.ahub.repositoty.EmailGroupRepository;
import com.ydova.cv.YdovaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailGroupService {

    private final EmailGroupRepository emailGroupRepository;

    @Autowired
    public EmailGroupService(EmailGroupRepository emailGroupRepository) {
        this.emailGroupRepository = emailGroupRepository;
    }

    public EmailGroup create(EmailGroup emailGroup) {
        return emailGroupRepository.save(emailGroup);
    }

    public List<EmailGroup> getAll() {
        return emailGroupRepository.findAll();
    }

    public Optional<EmailGroup> getById(Long id) {
        return emailGroupRepository.findById(id);
    }

    public EmailGroup update(Long id, EmailGroup updatedGroup) throws YdovaException {
        return emailGroupRepository.findById(id).map(group -> {
            group.setName(updatedGroup.getName());
            group.setEmails(updatedGroup.getEmails());
            return emailGroupRepository.save(group);
        }).orElseThrow(() -> new YdovaException("Email Group not found"));
    }

    public void delete(Long id) {
        emailGroupRepository.deleteById(id);
    }
}
