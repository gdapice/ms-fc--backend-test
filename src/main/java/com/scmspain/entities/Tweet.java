package com.scmspain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false, length = 2048)
    private String tweet;

    @Column
    private Long pre2015MigrationStatus = 0L;

    @JsonIgnore
    @Column(nullable = false)
    private Date publicationDate;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean discarded = Boolean.FALSE;

    @JsonIgnore
    @Column
    private Date discardedDate;

    public Tweet() {
    }

    public Tweet(String publisher, String text) {
        this.publisher = publisher;
        this.tweet = text;
        this.publicationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(Boolean discarded) {
        this.discarded = discarded;
    }

    public Date getDiscardedDate() {
        return discardedDate;
    }

    public void setDiscardedDate(Date discardedDate) {
        this.discardedDate = discardedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet1 = (Tweet) o;
        return Objects.equals(id, tweet1.id) &&
                Objects.equals(publisher, tweet1.publisher) &&
                Objects.equals(tweet, tweet1.tweet) &&
                Objects.equals(pre2015MigrationStatus, tweet1.pre2015MigrationStatus) &&
                Objects.equals(publicationDate, tweet1.publicationDate) &&
                Objects.equals(discarded, tweet1.discarded) &&
                Objects.equals(discardedDate, tweet1.discardedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publisher, tweet, pre2015MigrationStatus, publicationDate, discarded, discardedDate);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", publisher='" + publisher + '\'' +
                ", tweet='" + tweet + '\'' +
                ", pre2015MigrationStatus=" + pre2015MigrationStatus +
                ", publicationDate=" + publicationDate +
                ", discarded=" + discarded +
                ", discardedDate=" + discardedDate +
                '}';
    }
}
