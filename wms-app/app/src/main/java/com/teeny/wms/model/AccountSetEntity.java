package com.teeny.wms.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AccountSetEntity
 * @since 2017/8/14
 */

public class AccountSetEntity {

    @SerializedName(value = "key", alternate = {"databaseName"})
    private String databaseName;
    @SerializedName(value = "value", alternate = {"accountSetName"})
    private String accountSetName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getAccountSetName() {
        return accountSetName;
    }

    public void setAccountSetName(String accountSetName) {
        this.accountSetName = accountSetName;
    }

    @Override
    public String toString() {
        return accountSetName;
    }
}
