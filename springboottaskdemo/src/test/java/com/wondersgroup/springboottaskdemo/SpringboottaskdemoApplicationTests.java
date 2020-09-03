package com.wondersgroup.springboottaskdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringboottaskdemoApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    TemplateEngine templateEngine;

    /**
     * 简单邮件发送
     */
    @Test
    public void contextLoads() {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("通知-今晚开会");
        message.setText("今晚7:30开会");
        message.setFrom("2297227301@qq.com");
        message.setTo("cx2297227301@163.com");
        mailSender.send(message);
    }

    /**
     * html和附件邮件发送
     * @throws MessagingException
     */
    @Test
    public void Test01() throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setSubject("通知-今晚开会");
        mimeMessageHelper.setText("<b style='color:red'>今天7:30开会</b>",true);
        mimeMessageHelper.setFrom("2297227301@qq.com");
        mimeMessageHelper.setTo("cx2297227301@163.com");
        mimeMessageHelper.addAttachment("1.png",new File("C:\\1.png"));

        mailSender.send(mimeMessage);
    }

    /**
     * thymeleaf模板邮件发送
     * @throws MessagingException
     */
    @Test
    public void Test02() throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setSubject("通知-今晚开会");
        Context context=new Context();
        context.setVariable("msg","haha");
        mimeMessageHelper.setText(templateEngine.process("/EmailTemplate",context),true);
        mimeMessageHelper.setFrom("2297227301@qq.com");
        mimeMessageHelper.setTo("cx2297227301@163.com");

        mailSender.send(mimeMessage);
    }

}