package com.teeny.wms.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllowedMethodConfig
 * @since 2018/1/17
// */
@Configuration
public class AllowedMethodConfig {

    private TokenEndpoint mTokenEndpoint;

    @Autowired
    public void setTokenEndpoint(TokenEndpoint tokenEndpoint) {
        mTokenEndpoint = tokenEndpoint;
    }

    @PostConstruct
    public void reconfigure() {
        Set<HttpMethod> allowedMethods = new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
        mTokenEndpoint.setAllowedRequestMethods(allowedMethods);
    }
}
