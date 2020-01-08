package com.coachtam.tqt.vo.student.answer;

import com.coachtam.tqt.vo.admin.question.QuestionEditRequestVM;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitItemVM;
import lombok.Data;

@Data
public class QuestionAnswerVM {
    private QuestionEditRequestVM questionVM;
    private ExamPaperSubmitItemVM questionAnswerVM;
}
