package com.coachtam.tqt.viewmodel.student.exam;

import com.coachtam.tqt.viewmodel.student.base.BasePage;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExamPaperPageVM extends BasePage {
    @NotNull
    private Integer paperType;
    private String courseId;
//    private String courseId;
}
