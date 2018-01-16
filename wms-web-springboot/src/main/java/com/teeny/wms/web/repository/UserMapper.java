package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UserMapper
 * @since 2017/10/19
 */
@Repository
@Mapper
public interface UserMapper {

    UserEntity getUser(@Param("account") String account, @Param("username") String username);
}
