package com.coachtam.tqt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-28 16:40
 * @Description:
 */
@Data
@Entity
@Table(name="COURSE_P")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Course {

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "state")
    private Integer state;
    @Column(name = "order_no")
    private Integer orderNo;
}
