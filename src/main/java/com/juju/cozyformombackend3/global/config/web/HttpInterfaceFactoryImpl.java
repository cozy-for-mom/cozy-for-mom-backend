package com.juju.cozyformombackend3.global.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration // TODO: vs Component
public class HttpInterfaceFactoryImpl implements HttpInterfaceFactory {

    public <S> S create(Class<S> clientClass, RestClient restClient) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(clientClass);
    }
}
