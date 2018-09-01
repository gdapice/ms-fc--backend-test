package com.scmspain.validators;

import com.scmspain.entities.Tweet;
import org.springframework.util.StringUtils;

public class TweetValidator {

    public void validateTweet(Tweet tweet) throws IllegalArgumentException {
        validatePublisher(tweet.getPublisher());
        validateText(tweet.getTweet());
    }

    private void validatePublisher(String publisher) throws IllegalArgumentException {
        if (StringUtils.isEmpty(publisher)) {
            throw new IllegalArgumentException("Publisher must not be empty");
        }
    }

    private void validateText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Tweet must not be empty");
        }
        if (removeLinksFromTweet(text).length() <= 0 || removeLinksFromTweet(text).length() > 140) {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

    private String removeLinksFromTweet(String text) {
        return text.replaceAll("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "");
    }
}