package com.coachtam.tqt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name="EXAM_PAPER_CONTENT_P")
public class ExamPaperContent implements Serializable {


    private static final long serialVersionUID = -1279530310964668131L;

    public ExamPaperContent(){

    }

    public ExamPaperContent(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private Date createTime;

}
