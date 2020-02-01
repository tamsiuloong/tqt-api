package com.coachtam.tqt.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String id;

    private String username;

    private List<String> roles;


    public UserInfo(String id, String username) {
        this.id = id;
        this.username = username;
    }

}
