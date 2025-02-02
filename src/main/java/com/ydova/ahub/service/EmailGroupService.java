package com.ydova.ahub.service;

import com.ydova.ahub.dto.EmailGroupDto;
import com.ydova.ahub.entity.Email;
import com.ydova.ahub.entity.EmailGroup;

import com.ydova.ahub.repository.EmailGroupRepository;
import com.ydova.ahub.repository.EmailRepository;
import com.ydova.cv.YdovaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailGroupService {

    private final EmailGroupRepository emailGroupRepository;
    private final EmailRepository emailRepository;

    @Autowired
    public EmailGroupService(EmailGroupRepository emailGroupRepository, EmailRepository emailRepository) {
        this.emailGroupRepository = emailGroupRepository;
        this.emailRepository = emailRepository;
    }

    public EmailGroup create(EmailGroupDto emailGroup) {
        return emailGroupRepository.save(EmailGroup.builder().id(emailGroup.getId()).name(emailGroup.getName()).build());
    }

    public List<EmailGroupDto> getAll() {
        List<EmailGroup> emailGroups =  emailGroupRepository.findAll();
        List<EmailGroupDto> res = new ArrayList<>();
        for (EmailGroup emailGroup : emailGroups) {
            List<Email> emails = emailRepository.findByGroupName(emailGroup.getName());
            EmailGroupDto dto = EmailGroupDto.builder().id(emailGroup.getId()).name(emailGroup.getName()).emails(emails).build();
            res.add(dto);
        }
       return res;
    }

    public Optional<EmailGroupDto> getById(Long id) {
     Optional<EmailGroup> emailGroup = emailGroupRepository.findById(id);
     if (emailGroup.isPresent()) {
         List<Email> emails = emailRepository.findByGroupName(emailGroup.get().getName());
         EmailGroupDto dto = EmailGroupDto.builder().id(emailGroup.get().getId()).name(emailGroup.get().getName()).emails(emails).build();
        return Optional.of(dto);
     }
     return Optional.empty();
    }

    public EmailGroupDto update(Long id, EmailGroupDto updatedGroup) throws YdovaException {
         emailGroupRepository.findById(id).map(group -> {
            List<Email> emails = emailRepository.findByGroupName(group.getName());
             return EmailGroupDto.builder().id(group.getId()).name(group.getName()).emails(emails).build();
        });
         throw  new YdovaException("Email Group not found");
    }

    public void delete(Long id) {
        emailGroupRepository.deleteById(id);
    }
}
