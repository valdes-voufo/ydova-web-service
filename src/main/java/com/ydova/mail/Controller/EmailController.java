package com.ydova.mail.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydova.Log;
import com.ydova.mail.dto.EmailDto;
import com.ydova.mail.dto.EmailSendingResponseDto;
import com.ydova.mail.service.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EmailController {
    private final GmailService gmailService;

    @Autowired
    public EmailController( GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @PostMapping("/send-mail")
    public List<EmailSendingResponseDto> sendMail(@RequestPart("files") MultipartFile[] files,
                                                  @RequestPart("content") String otherData ) {

        ObjectMapper objectMapper = new ObjectMapper();
        EmailDto email;
        try {
             email = objectMapper.readValue(otherData, EmailDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



     List<File>   convertedFiles = Arrays.stream(files).map(this::convertMultipartFileToFile).toList();
        email.setAttachments(new ArrayList<>(convertedFiles));

        Log.info("sending mail:"+email,this.getClass());

     return gmailService.sendEmail(email);
    }




    @GetMapping("/")
    public String get() {
        return "Welcome to Ydova mail Service";
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
