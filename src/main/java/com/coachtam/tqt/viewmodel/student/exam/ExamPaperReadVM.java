package com.coachtam.tqt.viewmodel.student.exam;

import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperEditRequestVM;
import lombok.Data;

@Data
public class ExamPaperReadVM {
    private ExamPaperEditRequestVM paper;
    private ExamPaperSubmitVM answer;
}
