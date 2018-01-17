package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.config.security.UserService;
import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.UserWrapper;
import com.teeny.wms.web.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UserServiceImpl
 * @since 2018/1/15
 */
@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper mMapper;

    @Autowired
    public void setMapper(UserMapper mapper) {
        mMapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        String[] temp = username.split("@");
        UserEntity user = mMapper.getUser(temp[1], temp[0]);
        if (user == null) {
            throw new InnerException("User not Found");
        }
        return new UserWrapper(user);
    }
}
