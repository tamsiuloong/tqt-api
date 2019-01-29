package com.coachtam.tqt.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-28 16:40
 * @Description:
 */
@Data
@Entity
@Table(name="t_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
