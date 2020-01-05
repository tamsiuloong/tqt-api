package com.coachtam.tqt.vo.question;

import com.coachtam.tqt.vo.exam.BasePage;
import lombok.Data;

@Data
public class QuestionPageRequestVM extends BasePage {

    private Integer id;
    private Integer level;
    private Integer subjectId;
    private Integer questionType;
}
