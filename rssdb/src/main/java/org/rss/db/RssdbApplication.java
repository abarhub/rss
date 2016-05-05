package org.rss.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("org.rss.db")
@EnableAutoConfiguration
@Configuration
public class RssdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssdbApplication.class, args);
    }
}
