package com.scmspain.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITweetRepository<T, U> {

    void create(T tweet);

    T get(U id);

    List<T> getAll(Boolean discarded);

    void discard(T tweet);

}