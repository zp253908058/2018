package com.teeny.wms.model;

import com.teeny.wms.util.PreferencesUtils;
import com.teeny.wms.util.Validator;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ServerConfigEntity
 * @since 2017/7/14
 */
@Entity(nameInDb = "server_config")
public class ServerConfigEntity {
    @Id(autoincrement = true)
    @PreferencesUtils.Ignore
    private Long id;
    @Property(nameInDb = "server_name")
    private String serverName;
    @Property(nameInDb = "server_address")
    private String serverAddress;
    private String port;

    @Generated(hash = 684001065)
    public ServerConfigEntity(Long id, String serverName, String serverAddress,
                              String port) {
        this.id = id;
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.port = port;
    }

    @Generated(hash = 746732654)
    public ServerConfigEntity() {
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return Validator.isEmpty(serverAddress);
    }

    @Override
    public String toString() {
        return "ServerConfigEntity{" +
                "id=" + id +
                ", serverName='" + serverName + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
