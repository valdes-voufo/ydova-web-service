package com.ydova.mail.service;

import com.ydova.Log;
import com.ydova.ahub.entity.Application;
import com.ydova.ahub.entity.Email;
import com.ydova.ahub.repository.ApplicationRepository;
import com.ydova.ahub.service.EmailService;
import com.ydova.mail.dto.EmailDto;
import com.ydova.mail.dto.EmailDto2;
import com.ydova.mail.dto.EmailSendingResponseDto;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class GmailService {

private final ApplicationRepository applicationRepository;


@Autowired
    public GmailService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<EmailSendingResponseDto> sendEmail(EmailDto2 emailDto) {
        List<EmailSendingResponseDto> res = new ArrayList<>();
        String[] emailList ;

        emailList = emailDto.getRecipients().split(",");


        for (String recipient : emailList) {
            MimeMessagePreparator preparator = mimeMessage -> {
                // Use MimeMessageHelper for easier email construction
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                // Set the basic email properties
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                mimeMessage.setFrom(new InternetAddress(emailDto.getSender()));
                mimeMessage.setSubject(emailDto.getSubject());
                helper.setText(emailDto.getBody());  // You can customize this message content as needed

                // Check if there are attachments and add them
                if (emailDto.getAttachments() != null) {
                    for (File att : emailDto.getAttachments()) {
                        // Use FileSystemResource to read the file

                        FileSystemResource file = new FileSystemResource(att);
                        helper.addAttachment(att.getName(), file);  // You can change MIME type as needed
                    }
                }
            };

            try {
                // Configure the mail sender using the provided credentials
                JavaMailSender mailSender = configureMailSender(emailDto.getSender(), emailDto.getPassword());

                // Send the email
                mailSender.send(preparator);
                res.add(new EmailSendingResponseDto(recipient, true));

                Application application = new Application();
                application.setSender(emailDto.getSender());
                application.setReceiver(recipient);
                application.setSuccess(true);
                applicationRepository.save(application);
                // Log success
                Log.info("Email successfully sent to " + recipient, this.getClass());
            } catch (MailException e) {
                Application application = new Application();
                application.setSender(emailDto.getSender());
                application.setReceiver(recipient);
                application.setSuccess(false);
                applicationRepository.save(application);
                res.add(new EmailSendingResponseDto(recipient, false));
                Log.info("Exception occurred while sending email to " + recipient, this.getClass());
                Log.info("Error: " + e.getMessage(), this.getClass());
            }
        }

        return res;
    }


    // Configures the JavaMailSender
    public JavaMailSender configureMailSender(String email, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            Log.error("Error while converting the MultipartFile to a File");
        }
        return file;
    }
}
