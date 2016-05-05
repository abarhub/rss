package org.rss.read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class RssReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssReadApplication.class, args);
    }
}
