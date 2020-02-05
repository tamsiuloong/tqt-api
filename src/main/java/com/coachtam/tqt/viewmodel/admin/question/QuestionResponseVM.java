package com.coachtam.tqt.viewmodel.admin.question;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.entity.QuestionItems;
import com.coachtam.tqt.utils.StringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseVM {

    private Integer id;

    @JsonSerialize(using = StringSerializer.class)
    private Integer questionType;

    private String createTime;

    private String courseName;

    private String createUser;

    private Integer score;

    private Integer difficult;

    private String shortTitle;

    private String title;

    private QuestionItems questionItems;

//        private Integer status;
//
//    private String analyze;

    private Course course;
//
//    private Integer analyzeTextContentId;
//        private Integer textContentId;

}
