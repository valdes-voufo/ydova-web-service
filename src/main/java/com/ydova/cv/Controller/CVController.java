package com.ydova.cv.Controller;

import com.ydova.Log;
import com.ydova.ahub.entity.AHubClient;
import com.ydova.cv.YdovaException;
import com.ydova.cv.service.util.DefaultCVGenerator;
import com.ydova.mail.dto.EmailDto;
import com.ydova.mail.service.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CVController {

    private  final GmailService gmailService ;

    @Autowired
    public CVController(GmailService gmailService) {
        this.gmailService = gmailService;
    }



    @PostMapping("/cv")
    public void buildCV(@RequestBody AHubClient client) {
        DefaultCVGenerator defaultCvGenerator = new DefaultCVGenerator() ;
        client.setStatus("Student");  //todo fixme


        Log.info("Building CV:"+ client,this.getClass());

        File cv;
        try {
            cv = defaultCvGenerator.generate(client);
        } catch (YdovaException e) {
            throw new RuntimeException(e);
        }

        Log.info("sending mail:"+cv,this.getClass());


        EmailDto emailDto = EmailDto.builder()
                .sender("valdesvoufo2@gmail.com")
                .recipients("valdesvoufo8@gmail.com")
                .body("Your CV build by YDOVA")
                .subject("Your CV Build by Ydova")
                .attachments(List.of(cv))
                .password("graqobggufjcfdsp").build();

        gmailService.sendEmail(emailDto);
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
