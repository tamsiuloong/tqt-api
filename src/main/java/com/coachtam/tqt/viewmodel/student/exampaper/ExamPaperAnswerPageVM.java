package com.coachtam.tqt.viewmodel.student.exampaper;

import com.coachtam.tqt.viewmodel.student.base.BasePage;
import lombok.Data;

@Data
public class ExamPaperAnswerPageVM extends BasePage {

    private String courseId;

    private String createUser;

    private Integer examPaperId;

}
