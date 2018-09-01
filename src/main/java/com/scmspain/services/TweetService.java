package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.exception.NotFoundException;
import com.scmspain.repository.ITweetRepository;
import com.scmspain.validators.TweetValidator;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TweetService {

    private ITweetRepository<Tweet, Long> repository;
    private MetricWriter metricWriter;
    private TweetValidator validator;

    /**
     * Constructor of the service
     *
     * @param repository   the repository
     * @param metricWriter the metric writer
     * @param validator    the tweet validator
     */
    public TweetService(ITweetRepository repository, MetricWriter metricWriter, TweetValidator validator) {
        this.repository = repository;
        this.metricWriter = metricWriter;
        this.validator = validator;
    }

    /**
     * Push tweet to repository
     *
     * @param publisher the publisher of the tweet
     * @param text      the body of the tweet
     * @throws IllegalArgumentException exception thrown if requirements are not met
     */
    public void publishTweet(String publisher, String text) throws IllegalArgumentException {
        Tweet tweet = new Tweet(publisher, text);
        validator.validateTweet(tweet);
        this.repository.create(tweet);
        this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
    }

    /**
     * Recover tweet from repository
     *
     * @param id the Id of the Tweet
     * @return Tweet found by id
     */
    public Tweet getTweet(Long id) {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweet-by-id", 1));
        return this.repository.get(id);
    }

    /**
     * Recover published tweets from repository
     *
     * @return List<Tweet> list of published tweets
     */
    public List<Tweet> listAllPublishedTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-published-tweets", 1));
        return this.repository.getAll(Boolean.FALSE);
    }

    /**
     * Recover discarded tweets from repository
     *
     * @return List<Tweet> list of discarded tweets
     */
    public List<Tweet> listAllDiscardedTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-discarded-tweets", 1));
        return this.repository.getAll(Boolean.TRUE);
    }

    /**
     * Discards a tweet if exists in the database
     *
     * @param tweetId the Id of the Tweet
     */
    public void discardTweet(String tweetId) {
        Tweet tweet = this.repository.get(Long.valueOf(tweetId));
        if (tweet == null) {
            throw new NotFoundException("Tweet with id " + tweetId + " does not exist in the Database");
        }
        tweet.setDiscarded(Boolean.TRUE);
        tweet.setDiscardedDate(new Date());
        this.repository.discard(tweet);
        this.metricWriter.increment(new Delta<Number>("times-discarded-tweet", 1));
    }

}
