package com.scmspain;

import com.scmspain.configuration.InfrastructureConfiguration;
import com.scmspain.configuration.TweetConfiguration;
import com.scmspain.configuration.ValidatorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({TweetConfiguration.class, InfrastructureConfiguration.class, ValidatorConfiguration.class})
public class MsFcTechTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFcTechTestApplication.class, args);
    }

}
