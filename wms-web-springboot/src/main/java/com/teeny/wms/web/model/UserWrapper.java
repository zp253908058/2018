package com.teeny.wms.web.model;

import com.teeny.wms.app.config.security.GrantedAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UserWrapper
 * @since 2018/1/15
 */
public class UserWrapper implements UserDetails {

    private UserEntity mUserEntity;

    public UserWrapper(UserEntity entity) {
        super();
        this.mUserEntity = entity;
    }

    public UserEntity getUserEntity() {
        return mUserEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.mUserEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthorityImpl authority = new GrantedAuthorityImpl();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return mUserEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return mUserEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return mUserEntity.toString();
    }
}
