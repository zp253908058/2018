package com.teeny.wms.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AppConfig
 * @since 2018/1/15
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userHandlerMethodArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver userHandlerMethodArgumentResolver() {
        return new UserHandlerMethodArgumentResolver();
    }
}
