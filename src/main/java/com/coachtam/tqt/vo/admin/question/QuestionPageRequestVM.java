package com.coachtam.tqt.vo.admin.question;

import com.coachtam.tqt.vo.admin.exam.BasePage;
import lombok.Data;

@Data
public class QuestionPageRequestVM extends BasePage {

    private Integer id;
    private Integer level;
    private Integer subjectId;
    private Integer questionType;
}
