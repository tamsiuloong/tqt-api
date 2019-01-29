package com.coachtam.tqt.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-28 16:46
 * @Description:
 */
@Data
@Entity
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;


}
