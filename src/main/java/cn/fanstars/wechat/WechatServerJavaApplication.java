package cn.fanstars.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class WechatServerJavaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WechatServerJavaApplication.class, args);
        Environment env = run.getEnvironment();

        String ipAddress = "unknown";
        String port = env.getProperty("server.port");

        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info("get ipAddress is error: ", e);
        }

        log.info("----------------------------------------------------------");
        log.info("Application '{}' is running!", env.getProperty("spring.application.name"));
        log.info("Access URLs:");
        log.info("Local:    http://localhost:{}/", port);
        log.info("External: http://{}:{}/", ipAddress, port);
        log.info("----------------------------------------------------------");
    }

}