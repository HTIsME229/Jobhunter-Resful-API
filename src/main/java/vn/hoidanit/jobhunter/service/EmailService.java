package vn.hoidanit.jobhunter.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.res.RestEmailJob;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public List<RestEmailJob> hadleConvertListJob(List<Job> jobList) {
        List<RestEmailJob> restEmailJobList = new ArrayList<>();
        for (Job job : jobList) {
            RestEmailJob restEmailJob = new RestEmailJob();
            restEmailJob.setName(job.getName());
            restEmailJob.setSalary(job.getSalary());
            RestEmailJob.CompanyEmail companyEmail = new RestEmailJob.CompanyEmail();
            companyEmail.setName(job.getCompany().getName());
            restEmailJob.setCompany(companyEmail);
            List<RestEmailJob.SkillEmail> skillEmailList = new ArrayList<>();
            for (Skills skill : job.getSkills()) {
                RestEmailJob.SkillEmail skillEmail = new RestEmailJob.SkillEmail();
                skillEmail.setName(skill.getName());
                skillEmailList.add(skillEmail);
            }
            restEmailJob.setSkills(skillEmailList);
            restEmailJobList.add(restEmailJob);


        }
        return restEmailJobList;
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

    @Async
    public void sendEmailFromTemplateSync(List<String> to, List<RestEmailJob> jobList, String subject, String
            templateName) {
        Context context = new Context();
        context.setVariable("jobList", jobList);
        String content = this.templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }


}
