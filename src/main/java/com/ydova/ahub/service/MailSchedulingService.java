package com.ydova.ahub.service;

import com.ydova.ahub.entity.ApplicationJob;
import com.ydova.ahub.repository.ApplicationJobRepository;

import com.ydova.cv.YdovaException;
import com.ydova.mail.dto.EmailDto2;
import com.ydova.mail.service.GmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MailSchedulingService {


    private  final ApplicationJobRepository applicationJobRepository;
    private  final ApplicationService applicationService;
    private  final GmailService gmailService;

    @Autowired
    public MailSchedulingService(ApplicationJobRepository applicationJobRepository, ApplicationService applicationService, GmailService gmailService) {
        this.applicationJobRepository = applicationJobRepository;
        this.applicationService = applicationService;
        this.gmailService = gmailService;
    }


    @Scheduled(fixedRate = 2000000)
    @Transactional
        public void performDailyTask() {
            processAndRemoveJobs();
        }








    public void processAndRemoveJobs() {
        // Step 1: Retrieve all ApplicationJobs from the database
        List<ApplicationJob> allJobs = applicationJobRepository.findAll();

        // Step 2: Group the jobs by sender
        Map<String, List<ApplicationJob>> jobsGroupedBySender = allJobs.stream().filter(job -> !Objects.equals(job.getSender(), "mawutokoumi87@gmail.com"))
                .collect(Collectors.groupingBy(ApplicationJob::getSender));

        // Step 3: Iterate over each sender group and process the jobs
        jobsGroupedBySender.forEach((sender, jobs) -> {
            // Step 3.1: Sort jobs by emailApplicationID (or any other sorting criterion)
            List<ApplicationJob> sortedJobs = jobs.stream()
                    .sorted(Comparator.comparing(ApplicationJob::getEmailApplicationID))
                    .toList();

            // Step 3.2: Select the top 300 jobs
            List<ApplicationJob> top300Jobs = sortedJobs.stream()
                    .limit(10)
                    .collect(Collectors.toList());

            // Step 3.3: Perform the function on each job (example: sending an email)
            top300Jobs.forEach(this::performFunctionOnJob);

            // Step 3.4: Remove the processed jobs from the database
            applicationJobRepository.deleteAll(top300Jobs);
        });
    }

    // Example function that you would perform on each job
    private void performFunctionOnJob(ApplicationJob job)  {


        EmailDto2 emailDto2;
        try {
            emailDto2 = applicationService.getEmailApplicationByWithoutRecipient(job.getEmailApplicationID());
        } catch (YdovaException e) {
            throw new RuntimeException(e);
        }
        emailDto2.setRecipients(job.getReceiver());

      gmailService.sendEmail(emailDto2);

    }

}
