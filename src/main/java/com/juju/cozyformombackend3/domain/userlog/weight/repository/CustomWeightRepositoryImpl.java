package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomWeightRepositoryImpl implements CustomWeightRepository {

	private final JPAQueryFactory queryFactory;
}