package com.coachtam.tqt.viewmodel.admin.exam;

import lombok.Data;

@Data
public class ExamResponseVM {
    private Integer id;

    private String name;

    private Integer questionCount;

    private Integer score;

    private String createTime;

    private String createUser;

    private String courseName;

    private String classesName;

    private Integer paperType;

    private Integer frameTextContentId;

    private Boolean deleted;

}
