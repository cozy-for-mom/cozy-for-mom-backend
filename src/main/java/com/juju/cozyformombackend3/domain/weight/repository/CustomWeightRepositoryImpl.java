package com.juju.cozyformombackend3.domain.weight.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class CustomWeightRepositoryImpl implements CustomWeightRepository {

    private final JPAQueryFactory queryFactory;

    public CustomWeightRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
