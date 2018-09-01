package com.scmspain.repository;

import com.scmspain.entities.Tweet;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TweetRepository implements ITweetRepository<Tweet, Long> {

    private EntityManager entityManager;

    public TweetRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Tweet tweet) {
        this.entityManager.persist(tweet);
    }

    @Override
    public Tweet get(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

    @Override
    public List<Tweet> getAll(Boolean discarded) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root<Tweet> tweet = criteriaQuery.from(Tweet.class);
        criteriaQuery.select(tweet);
        criteriaQuery.where(
                criteriaBuilder.notEqual(tweet.get("pre2015MigrationStatus"), 99),
                criteriaBuilder.equal(tweet.get("discarded"), discarded)
        );
        if (Boolean.FALSE.equals(discarded)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(tweet.get("publicationDate")));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(tweet.get("discardedDate")));
        }
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void discard(Tweet tweet) {
        this.entityManager.merge(tweet);
    }
}
