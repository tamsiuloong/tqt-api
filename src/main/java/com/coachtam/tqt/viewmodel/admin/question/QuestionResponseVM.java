package com.coachtam.tqt.viewmodel.admin.question;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseVM {

    private Integer id;

    private Integer questionType;

    private String createTime;

    private String courseName;

    private String createUser;

    private Integer score;

    private Integer difficult;

    private String shortTitle;


//        private Integer status;
//
//    private String correct;
//
//    private Integer analyzeTextContentId;
//        private Integer textContentId;

}
