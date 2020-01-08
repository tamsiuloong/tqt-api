package com.coachtam.tqt.viewmodel.student.answer;

import com.coachtam.tqt.viewmodel.admin.question.QuestionEditRequestVM;
import com.coachtam.tqt.viewmodel.student.exam.ExamPaperSubmitItemVM;
import lombok.Data;

@Data
public class QuestionAnswerVM {
    private QuestionEditRequestVM questionVM;
    private ExamPaperSubmitItemVM questionAnswerVM;
}
