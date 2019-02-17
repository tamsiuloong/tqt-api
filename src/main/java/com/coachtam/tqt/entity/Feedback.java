package com.coachtam.tqt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name="FEEDBACK_P")
@JsonIgnoreProperties({"user"})
public class Feedback {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id")
    private String id;
    /*日期*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "back_time")
    private Date backTime;
    /*计划*/
    @Column(name = "plan")
    private String plan;
    /*打算怎么做*/
    @Column(name = "todo")
    private String todo;
    /*自我觉察*/
    @Column(name = "self_check")
    private String selfCheck;
    /*调整*/
    @Column(name = "adjustment")
    private String adjustment;

    /*不清楚*/
    @Column(name = "not_clear")
    private String  notClear;

    /*课程第几天*/
    @Column(name = "day_num")
    private Integer dayNum;

    /*吸收程度*/
    @Column(name = "absorption")
    private String absorption;
    /*所属课程*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    /*所属用户*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Transient
    private String stuName;
    /*学生名字*/
    public String getStuName() {
        if(user!=null&&user.getUserInfo()!=null)
        {
            stuName = user.getUserInfo().getName();
        }
        return stuName;
    }
}
