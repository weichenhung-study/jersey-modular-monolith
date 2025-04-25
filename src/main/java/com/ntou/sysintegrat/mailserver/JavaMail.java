package com.ntou.sysintegrat.mailserver;

import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Log4j2
public class JavaMail {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            log.info("ExecutorService 已關閉");
        }));
    }

    public void sendMail(MailVO vo) {
        executor.submit(() -> {
            try {

                final String userName = "weiiweiidev@gmail.com";//"tuluber@gmail.com"; // 寄件者信箱
                final String password = "gevaewtbwiwhedea";// 寄件者密碼 https://www.shin-her.com.tw/R/07583ed
                Transport transport = null;
                Properties prop = getProperties();

                Auth auth = new Auth(userName, password);
                Session session = Session.getDefaultInstance(prop, auth);

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

                    // transport將message送出
                    Transport.send(message);

                } catch (MessagingException e) {
                    log.error(Common.EXCEPTION, e);
                } finally {
                    try {
                        if(transport!=null) transport.close();
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

        // 顯示連線資訊
        prop.put("mail.debug", "true");
        return prop;
    }
}
