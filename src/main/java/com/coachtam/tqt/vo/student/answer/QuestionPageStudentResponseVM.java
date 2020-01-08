package com.coachtam.tqt.vo.student.answer;

import lombok.Data;

@Data
public class QuestionPageStudentResponseVM {
    private Integer id;

    private Integer questionType;

    private String createTime;

    private String courseName;

    private String shortTitle;
}
