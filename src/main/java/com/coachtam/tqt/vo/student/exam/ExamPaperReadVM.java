package com.coachtam.tqt.vo.student.exam;

import com.coachtam.tqt.vo.admin.exam.ExamPaperEditRequestVM;
import lombok.Data;

@Data
public class ExamPaperReadVM {
    private ExamPaperEditRequestVM paper;
    private ExamPaperSubmitVM answer;
}
