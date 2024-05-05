package com.juju.cozyformombackend3.global.config.web;

import org.springframework.web.client.RestClient;

public interface HttpInterfaceFactory {
    <S> S create(Class<S> clientClass, RestClient restClient);
    // 출처: https://myvelop.tistory.com/217#RestClient란%3F-1 [MYVELOP 마이벨롭:티스토리]
}
