package com.scmspain.configuration;

import com.scmspain.validators.TweetValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public TweetValidator tweetValidator() {
        return new TweetValidator();
    }
}