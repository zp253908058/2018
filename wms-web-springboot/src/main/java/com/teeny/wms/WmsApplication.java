package com.teeny.wms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
public class WmsApplication implements CommandLineRunner {

    private static final Logger sLogger = LoggerFactory.getLogger(WmsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        sLogger.info("server started.");
    }
}
