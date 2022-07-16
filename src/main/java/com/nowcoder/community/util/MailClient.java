package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 定义一个发送邮箱的类
 */
@Component
public class MailClient {

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    // 将配置文件的属性注入
    @Value("${spring.mail.username}")
    private String from;

    /**
     * // 发送邮箱的函数
     * @param to  接收邮件的邮箱（收件人）
     * @param subject  邮件的主题
     * @param content  邮件的内容
     */
    public void sendMail(String to, String subject, String content){
        try {

            // 构建邮件内容
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            // 设置发件人
            helper.setFrom(from);
            // 设置收件人
            helper.setTo(to);
            // 设置邮件主题
            helper.setSubject(subject);
            //设置邮件内容
            // 加了true参数，表示支持发送html文件，因此加了true
            helper.setText(content, true);

            // 发送邮件
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }

    }
}
