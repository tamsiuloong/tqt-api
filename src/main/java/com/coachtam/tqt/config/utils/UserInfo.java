package com.coachtam.tqt.config.utils;

/**
 * 用户信息
 */
public class UserInfo {

    private String id;

    private String username;

    public UserInfo() {
    }

    public UserInfo(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
