package com.ydova.ahub.service;

import com.ydova.ahub.entity.ApplicationJob;
import com.ydova.ahub.entity.EmailApplication;
import com.ydova.ahub.repository.ApplicationJobRepository;
import com.ydova.ahub.repository.EmailApplicationRepository;
import com.ydova.cv.YdovaException;
import com.ydova.mail.dto.EmailDto;
import com.ydova.mail.dto.EmailDto2;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {


        private final EmailApplicationRepository emailRepository;
        private final ApplicationJobRepository applicationJobRepository;



        public ApplicationService(EmailApplicationRepository emailRepository, ApplicationJobRepository applicationJobRepository) {
            this.emailRepository = emailRepository;
            this.applicationJobRepository = applicationJobRepository;
        }

        @Transactional
        public void saveJob(EmailDto emailDto) {
            EmailApplication emailApplication = new EmailApplication();
            emailApplication.setSender(emailDto.getSender());
            emailApplication.setRecipients(emailDto.getRecipients());
            emailApplication.setSubject(emailDto.getSubject());
            emailApplication.setBody(emailDto.getBody());


            List<String> attachmentNames = new ArrayList<>();
            List<String> attachmentTypes = new ArrayList<>();
            List<byte[]> attachmentData = new ArrayList<>();

            // Handle each attachment
            if (emailDto.getAttachments() != null) {
                for (MultipartFile attachment : emailDto.getAttachments()) {
                    if (!attachment.isEmpty()) {
                        try {

                            attachmentNames.add(attachment.getOriginalFilename());
                            attachmentTypes.add(attachment.getContentType());
                            attachmentData.add(attachment.getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save attachment", e);
                        }
                    }
                }
            }

            // Set all attachment details in the email application entity

            emailApplication.setAttachmentNames(attachmentNames);
            emailApplication.setAttachmentTypes(attachmentTypes);
            emailApplication.setAttachmentData(attachmentData);
            emailApplication.setPassword(emailDto.getPassword());
            emailApplication.setBody(emailDto.getBody());
            emailApplication.setRecipients(emailApplication.getRecipients());
            emailApplication.setSender(emailDto.getSender());
            emailApplication.setSubject(emailDto.getSubject());
            // Save the email entity to the database
         EmailApplication application =    emailRepository.save(emailApplication);



         String[] emailList = emailDto.getRecipients().split(",");

         for (String email : emailList) {
           var job =  new ApplicationJob();
           job.setReceiver(email);
           job.setSender(application.getSender());
           job.setEmailApplicationID(application.getId());
           applicationJobRepository.save(job);

         }


        }


        public EmailDto2 getEmailApplicationByWithoutRecipient(Long id) throws YdovaException {
         EmailApplication emailApplication =  emailRepository.getAllById(id);


         if (emailApplication == null) {
             return null;
         }
         else {
             EmailDto2  emailDto= new EmailDto2();
             emailDto.setBody(emailApplication.getBody());
             emailDto.setSender(emailApplication.getSender());
             emailDto.setSubject(emailApplication.getSubject());
             try {
                 emailDto.setAttachments(retrieveJobWithAttachments(emailApplication));
             } catch (IOException e) {
                 throw new YdovaException(e.getMessage());
             }
             emailDto.setPassword(emailApplication.getPassword());
             return emailDto;
         }
        }








        public List<File> retrieveJobWithAttachments(EmailApplication emailApplication) throws IOException {


            // Retrieve attachment data from the database
            List<byte[]> attachmentData = emailApplication.getAttachmentData();
            List<String> attachmentNames = emailApplication.getAttachmentNames();

            // List to hold the File objects
            List<File> attachmentFiles = new ArrayList<>();

            // Convert byte[] data to File
            for (int i = 0; i < attachmentData.size(); i++) {
                byte[] fileData = attachmentData.get(i);
                String fileName = attachmentNames.get(i);

                // Define the path for the file (temporary location or a specific directory)
                Path targetDir = Paths.get("/tmp/ydova");
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir); // Create the directory if it doesn't exist
                }
                Path filePath = Paths.get("/tmp/ydova", fileName);

                // Create the file from byte array data
                File file = new File(filePath.toUri());
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(fileData);
                }

                // Add the created file to the list
                attachmentFiles.add(file);
            }

            // Return the list of File objects
            return attachmentFiles;
        }




}

