package com.avocat.service;

import com.avocat.exceptions.SendEmailException;
import com.avocat.security.jwt.JwtTokenSendEmail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserEmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.email.from}")
    private String emailFrom;

    @Autowired
    private JwtTokenSendEmail jwtTokenSendEmail;

    public void sendEmailForgotPassword(String email) {

        var link = jwtTokenSendEmail.generateTokenToSendEmail(email);

        Email from = new Email(emailFrom);
        String subject = "Avocat Support";
        Email to = new Email(email.trim());
        Content content = new Content("text/plain", """
                Acesse o link para cadastrar sua nova senha e ter acesso na plataforma.
                
                %s""".formatted(link));
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() != 202) {
                throw new SendEmailException("error send email forgot password");
            }
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}