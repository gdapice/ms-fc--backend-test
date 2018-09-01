package com.scmspain.controller;

import com.scmspain.controller.command.TweetCommand;
import com.scmspain.entities.Tweet;
import com.scmspain.exception.NotFoundException;
import com.scmspain.services.TweetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class TweetController {

    private TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/tweet")
    public List<Tweet> listAllTweets() {
        return this.tweetService.listAllPublishedTweets();
    }

    @PostMapping(path = "/tweet", consumes = "application/json")
    @ResponseStatus(CREATED)
    public void publishTweet(@RequestBody TweetCommand tweetCommand) {
        this.tweetService.publishTweet(tweetCommand.getPublisher(), tweetCommand.getTweet());
    }

    @PostMapping("/discarded")
    @ResponseStatus(NO_CONTENT)
    public void discardTweet(@RequestBody TweetCommand tweetCommand) throws NotFoundException {
        this.tweetService.discardTweet(tweetCommand.getTweet());
    }

    @GetMapping("/discarded")
    public List<Tweet> listDiscardedTweets() {
        return this.tweetService.listAllDiscardedTweets();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public Object notFoundException(NotFoundException ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }
}
