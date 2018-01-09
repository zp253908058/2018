package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.AccountSetEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.UserEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginService
 * @since 2017/7/29
 */

public interface LoginService {

    /**
     * 测试接口
     *
     * @return null
     */
    @GET("/wms/log/test")
    Flowable<ResponseEntity<EmptyEntity>> test();

    /**
     * 登陆接口
     *
     * @param clientId  wms
     * @param grantType password
     * @param username  用户名@账套
     * @param password  密码
     * @return token集, 包含refresh_token和access_token
     */
    @GET("/wms/oauth/token")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Flowable<UserEntity> login(@Query(value = "username") String username, @Query("password") String password, @Query("client_id") String clientId, @Query("grant_type") String grantType);

    /**
     * 登出接口
     *
     * @return null
     */
    @POST("/wms/logout")
    Flowable<ResponseEntity<EmptyEntity>> logout();

    /**
     * 获取账套接口
     *
     * @return 账套集
     */
    @GET("/wms/log/accountSets")
    Flowable<ResponseEntity<List<AccountSetEntity>>> getAccountSets();

    /**
     * 刷新access_token
     *
     * @param refreshToken refresh_token
     * @return access_token
     */
    @POST("/wms/oauth/token")
    @FormUrlEncoded
    Flowable<ResponseEntity<String>> refreshToken(@Field("refresh_token") String refreshToken);


    /**
     * 刷新access_token
     *
     * @param refreshToken refresh_token
     * @return access_token
     */
    @GET("/wms/oauth/token")
    Call<UserEntity> refreshToken(@Query("refresh_token") String refreshToken, @Query("grant_type") String grantType, @Query("client_id") String clientId);
}
