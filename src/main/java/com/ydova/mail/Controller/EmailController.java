package com.ydova.mail.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydova.Log;
import com.ydova.mail.YdovaMailSendingException;
import com.ydova.mail.dto.EmailDto;
import com.ydova.mail.dto.EmailSendingResponseDto;
import com.ydova.mail.service.GmailService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@CrossOrigin(origins = "*") // Allows all origins
@RestController
public class EmailController {
    private final GmailService gmailService;

    @Autowired
    public EmailController( GmailService gmailService) {
        this.gmailService = gmailService;
    }


    @PostMapping("/send-mail")
    public List<EmailSendingResponseDto> sendMail(
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart("content") String otherData) {



        ObjectMapper objectMapper = new ObjectMapper();
        EmailDto email;
        try {
             email = objectMapper.readValue(otherData, EmailDto.class);
        } catch (JsonProcessingException e) {
            throw new YdovaMailSendingException(e.getMessage());
        }




        // Validate the EmailDto object
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<EmailDto>> violations = validator.validate(email);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:");
            for (ConstraintViolation<EmailDto> violation : violations) {
                errorMessage.append(" ").append(violation.getPropertyPath())
                        .append(": ").append(violation.getMessage()).append(";");
            }
            throw new YdovaMailSendingException(errorMessage.toString());
        }


   if (files  != null){
       List<File>   convertedFiles = Arrays.stream(files).map(this::convertMultipartFileToFile).toList();
       email.setAttachments(new ArrayList<>(convertedFiles));
   }


        Log.info("sending mail:"+email,this.getClass());

     return gmailService.sendEmail(email);
      //  return Collections.singletonList(new EmailSendingResponseDto(email.getRecipients(), true));
    }




    @GetMapping("/")
    public String get() {
        return "Welcome to YDOVA WEB SEVER 1";
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile)  {

        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            Log.error("Error While converting the Multipart file to a file");
        }
        return file;
    }


}
