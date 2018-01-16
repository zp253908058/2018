package com.teeny.wms.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AuthorizationServerConfiguration
 * @since 2018/1/16
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID = "wms";
    private static final String SCOPES = "secret";
    private static final String[] GRANT_TYPES = {"password", "refresh_token"};
    private static final String AUTHORITIES = "PLATFORM";
    private static final String CLIENT_SECRET = "secret";

    private AuthenticationManager mAuthenticationManager;

    private DataSource mDataSource;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        mAuthenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置两个客户端,一个用于password认证一个用于client认证
        clients.inMemory().withClient(CLIENT_ID)
                .resourceIds(ResourceServerConfiguration.RESOURCE_ID)
                .authorizedGrantTypes(GRANT_TYPES)
                .scopes(SCOPES)
                .authorities(AUTHORITIES)
                .secret(CLIENT_SECRET);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(mDataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).authenticationManager(mAuthenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }

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