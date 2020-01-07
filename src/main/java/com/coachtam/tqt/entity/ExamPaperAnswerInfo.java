package com.coachtam.tqt.entity;

import lombok.Data;

import java.util.List;

@Data
public class ExamPaperAnswerInfo {
    public ExamPaper examPaper;
    public ExamPaperAnswer examPaperAnswer;
    public List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers;
}
