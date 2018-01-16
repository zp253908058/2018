package com.teeny.wms.app.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see GrantedAuthorityImpl
 * @since 2018/1/15
 */

@Component
public class GrantedAuthorityImpl implements GrantedAuthority {

    public static final String GENERAL_USER = "ROLE_GENERAL_USER";

    public String getAuthority() {
        return GENERAL_USER;
    }
}
