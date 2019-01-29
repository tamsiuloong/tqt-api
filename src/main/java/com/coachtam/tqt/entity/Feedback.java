package com.coachtam.tqt.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-28 16:34
 * @Description: 教学反馈
 */
@Data
@Entity
@Table(name="t_feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*日期*/
    private Date backTime;
    /*计划*/
    private String plan;
    /*打算怎么做*/
    private String todo;
    /*自我觉察*/
    private String selfCheck;
    /*调整*/
    private String adjustment;

    /*不清楚*/
    private String  notClear;

    /*课程第几天*/
    private Integer dayNum;

    /*所属课程*/
    @ManyToOne
    private Course course;

    /*所属用户*/
    @ManyToOne
    private User user;




}
