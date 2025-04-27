package com.ntou.sysintegrat.mailserver;

import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Log4j2
public class JavaMail {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final String userName = "weiiweiidev@gmail.com"; // 寄件者信箱
    private static final String password = "gevaewtbwiwhedea"; // 寄件者密碼
    private static final Properties prop = getProperties();
    private static final Session session = Session.getInstance(prop, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    });

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            log.info("ExecutorService 已關閉");
        }));
    }

    public void sendMail(MailVO vo) {
        executor.submit(() -> {
            Transport transport = null;
            try {
                MimeMessage message = new MimeMessage(session);

                try {
                    InternetAddress sender = new InternetAddress(userName);
                    message.setSender(sender);

                    // 收件者
                    message.setRecipient(RecipientType.TO, new InternetAddress(vo.getEmailAddr()));

                    // 標題
                    message.setSubject(vo.getSubject());

                    // 內容/格式
                    message.setContent(vo.getContent(), "text/html;charset = UTF-8");

                    // ---------------------------------------------------------Transport傳送Message
                    transport = session.getTransport();
                    transport.connect(userName, password); // 顯式連接

                    // transport將message送出
                    transport.sendMessage(message, message.getAllRecipients());

                } catch (MessagingException e) {
                    log.error(Common.EXCEPTION, e);
                } finally {
                    try {
                        if (transport != null && transport.isConnected()) {
                            transport.close();
                        }
                    } catch (Exception e) {
                        log.error(Common.EXCEPTION, e);
                    }
                }
            } catch (Exception e) {
                log.error("非同步寄信失敗", e);
            }
        });
    }

    private static Properties getProperties() {
        Properties prop = new Properties();

        // 設定連線為smtp
        prop.setProperty("mail.transport.protocol", "smtp");

        // host主機:smtp.gmail.com
        prop.setProperty("mail.host", "smtp.gmail.com");

        // host port:465
        prop.put("mail.smtp.port", "465");

        // 寄件者帳號需要驗證：是
        prop.put("mail.smtp.auth", "true");

        // 需要安全資料傳輸層 (SSL)：是
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // 安全資料傳輸層 (SSL) 通訊埠：465
        prop.put("mail.smtp.socketFactory.port", "465");

        // 顯示連線資訊 (debug 模式，可以先開啟方便排查問題)
        prop.put("mail.debug", "true");
        return prop;
    }
}
