package com.juju.cozyformombackend3.global.config.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CozyLogSort;
import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new CozyLogSortConverter());
        registry.addConverter(new RecordPeriodConverter());
    }

    private static class CozyLogSortConverter implements Converter<String, CozyLogSort> {
        @Override
        public CozyLogSort convert(String keyword) {
            return CozyLogSort.of(keyword);
        }
    }

    private static class RecordPeriodConverter implements Converter<String, RecordPeriod> {
        @Override
        public RecordPeriod convert(String keyword) {
            return RecordPeriod.of(keyword);
        }
    }
}
