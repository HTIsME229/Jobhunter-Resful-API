package vn.hoidanit.jobhunter.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.SkillsRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.SecurityUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class EmailController {
    private MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;
    private SubscriberRepository subscriberRepository;
    private SkillsRepository skillsRepository;


    public EmailController(MailSender mailSender, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, SubscriberRepository subscriberRepository, SkillsRepository skillsRepository) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.subscriberRepository = subscriberRepository;
        this.skillsRepository = skillsRepository;
    }

    @GetMapping("/email")
    public String email() {
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
                sendEmailFromTemplateSync(emails, skills.getName(), "test");
            }

        }


        return "Hello World";
    }

    public void sendEmailSync(List<String> to, String subject, String content, boolean isMultipart,
                              boolean isHtml) {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                    isMultipart, StandardCharsets.UTF_8.name());

            String[] toArray = new String[to.size()];
            toArray = to.toArray(toArray);
            message.setTo(toArray);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            System.out.println("ERROR SEND EMAIL: " + e);
        }
    }

    public void sendEmailFromTemplateSync(List<String> to, String subject, String
            templateName) {
        Context context = new Context();
        String content = this.templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }
}
