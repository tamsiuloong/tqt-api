package com.coachtam.tqt.vo.student.exam;

import com.coachtam.tqt.vo.student.base.BasePage;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExamPaperPageVM extends BasePage {
    @NotNull
    private Integer paperType;
    private Integer subjectId;
    private Integer levelId;
}