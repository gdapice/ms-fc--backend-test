package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.exception.NotFoundException;
import com.scmspain.repository.TweetRepository;
import com.scmspain.validators.TweetValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TweetServiceTest {

    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetService tweetService;
    private TweetValidator tweetValidator;

    @Before
    public void setUp() {
        this.entityManager = mock(EntityManager.class);
        this.metricWriter = mock(MetricWriter.class);
        this.tweetValidator = new TweetValidator();
        this.tweetService = new TweetService(new TweetRepository(entityManager), metricWriter, tweetValidator);
    }

    @Test
    public void shouldInsertANewTweet() {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");
        verify(entityManager).persist(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetIsNull() {
        tweetService.publishTweet("Guybrush Threepwood", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetIsEmpty() {
        tweetService.publishTweet("Pirate", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenPublisherIsNull() {
        tweetService.publishTweet(null, "I am Guybrush Threepwood, mighty pirate.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenPublisherIsEmpty() {
        tweetService.publishTweet("", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }

    @Test
    public void shouldInsertANewTweetWithLinks() {
        tweetService.publishTweet("Pirate", "http://www.worldofmi.com/ LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to..");
        verify(entityManager).persist(any(Tweet.class));
    }

    @Test
    public void shouldDiscardAnExistingTweet() throws NotFoundException {
        Tweet tweet = new Tweet();
        tweet.setTweet("Lorem Ipsum is simply dummy text of the printing and typesetting industry");
        tweet.setPublisher("Gaston D'Apice");

        when(entityManager.find(Tweet.class, Long.valueOf("1406"))).thenReturn(tweet);

        tweetService.discardTweet("1406");

        Tweet discardedTweet = tweetService.getTweet(Long.valueOf("1406"));
        assertThat(discardedTweet.isDiscarded().equals(Boolean.TRUE));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowAnExceptionWhenTweetNotFound() throws NotFoundException {
        tweetService.discardTweet("1406");
    }
}
