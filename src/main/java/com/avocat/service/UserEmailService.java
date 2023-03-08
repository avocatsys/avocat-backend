package com.avocat.service;

import com.avocat.exceptions.SendEmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserEmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.email.from}")
    private String emailFrom;

    public void sendEmail(String email, String resource) {

        Email from = new Email(emailFrom);
        String subject = "Comece a usar a Avocat";
        Email to = new Email(email.trim());
        Content content = new Content("text/plain", """
                Você se cadastrou na Avocat.
                Acesse o link para cadastrar sua nova senha e ter acesso na plataforma.
                link: http:link""");
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