package vn.hoidanit.jobhunter.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.res.RestEmailJob;
import vn.hoidanit.jobhunter.repository.SkillsRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;
import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.SecurityUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class EmailController {

    private SubscriberRepository subscriberRepository;
    private SkillsRepository skillsRepository;
    private EmailService emailService;

    public EmailController(SubscriberRepository subscriberRepository, SkillsRepository skillsRepository, EmailService emailService) {

        this.subscriberRepository = subscriberRepository;
        this.skillsRepository = skillsRepository;
        this.emailService = emailService;
    }


    @GetMapping("/email")
    public String email(Model model) {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<Skills> skillsList = this.skillsRepository.findAll();
        for (Skills skills : skillsList) {
            List<String> emails = new ArrayList<>();
            for (Subscriber subscriber : subscribers) {
                if (subscriber.getSkills().contains(skills)) {
                    emails.add(subscriber.getEmail());
                }
            }
            if (emails.size() > 0) {
                List<Job> jobListBefore = skills.getJobs();
                String Title = skills.getName();
                List<RestEmailJob> jobList = this.emailService.hadleConvertListJob(jobListBefore);
                this.emailService.sendEmailFromTemplateSync(emails, jobList, Title, "test");
            }

        }


        return "Hello World";
    }


}
