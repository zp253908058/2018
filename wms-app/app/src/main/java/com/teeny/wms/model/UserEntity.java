package com.teeny.wms.model;

import com.google.gson.annotations.SerializedName;
import com.teeny.wms.util.Validator;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UserEntity
 * @since 2017/8/1
 */

public class UserEntity {

    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expiresIn;

    private String scope;

    private String accountSetName;

    private String databaseName;

    private String username;


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken == null ? "" : accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccountSetName() {
        return accountSetName;
    }

    public void setAccountSetName(String accountSetName) {
        this.accountSetName = accountSetName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public boolean isEmpty() {
        return Validator.isEmpty(refreshToken) || Validator.isEmpty(accessToken);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "refreshToken='" + refreshToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", scope='" + scope + '\'' +
                ", accountSetName='" + accountSetName + '\'' +
                ", databaseName='" + databaseName + '\'' +
                '}';
    }
}
