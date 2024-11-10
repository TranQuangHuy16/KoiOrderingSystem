package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.model.EmailDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    JavaMailSender javaMailSender;
    public void sendEmail(EmailDetail emailDetail){
        try{
            Context context = new Context();
            context.setVariable("name", emailDetail.getReceiver().getEmail());
            context.setVariable("button", "Go to home page");
            context.setVariable("link", emailDetail.getLink());

            String template = templateEngine.process("welcome", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);


            mimeMessageHelper.setFrom("koiorderingsystem@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println("ERROR SEND MAIL!");
        }
    }

    public void sendEmailForgotPassword(EmailDetail emailDetail){
        try{
            Context context = new Context();
            context.setVariable("name", emailDetail.getReceiver().getEmail());
            context.setVariable("button", "Go to change password page");
            context.setVariable("link", emailDetail.getLink());

            String template = templateEngine.process("forgotPassword", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);


            mimeMessageHelper.setFrom("koiorderingsystem@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println("ERROR SEND MAIL!");
        }
    }

    public void sendEmailNotificatePaymentBooking(EmailDetail emailDetail){
        try{
            Context context = new Context();
            context.setVariable("name", emailDetail.getReceiver().getEmail());
            context.setVariable("button", "Go to Payment page");
            context.setVariable("link", emailDetail.getLink());

            String template = templateEngine.process("notificatePaymentBooking", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);


            mimeMessageHelper.setFrom("koiorderingsystem@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println("ERROR SEND MAIL!");
        }
    }

    public void sendEmailNotificatePaymentOrder(EmailDetail emailDetail){
        try{
            Context context = new Context();
            context.setVariable("name", emailDetail.getReceiver().getEmail());
            context.setVariable("button", "Go to Payment page");
            context.setVariable("link", emailDetail.getLink());

            String template = templateEngine.process("notificatePaymentOrder", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);


            mimeMessageHelper.setFrom("koiorderingsystem@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println("ERROR SEND MAIL!");
        }
    }
}
