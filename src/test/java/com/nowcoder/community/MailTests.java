package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

// 测试类每次都要添加这几个注解
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    // 发送文本格式的文件
    @Test
    public void testTextMail(){
        mailClient.sendMail("uprightness@163.com", "TEST", "Welcome, this is the first time I use the Java program to send email to my 163Mail.");
    }

    // 发送HTML形式的格式

    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username", "sunday");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("uprightness@163.com", "HTML", content);
    }



}
