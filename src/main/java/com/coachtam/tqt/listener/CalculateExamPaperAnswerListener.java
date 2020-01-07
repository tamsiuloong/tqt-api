package com.coachtam.tqt.listener;


import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.entity.enums.ExamPaperTypeEnum;
import com.coachtam.tqt.entity.enums.QuestionTypeEnum;
import com.coachtam.tqt.event.CalculateExamPaperAnswerCompleteEvent;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.service.ExamPaperContentService;
import com.coachtam.tqt.service.ExamPaperQuestionCustomerAnswerService;
import com.coachtam.tqt.service.TaskExamCustomerAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;



@Component
@AllArgsConstructor
public class CalculateExamPaperAnswerListener implements ApplicationListener<CalculateExamPaperAnswerCompleteEvent> {


    private final ExamPaperAnswerService examPaperAnswerService;
    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final ExamPaperContentService textContentService;
    private final TaskExamCustomerAnswerService examCustomerAnswerService;

    @Override
    @Transactional
    public void onApplicationEvent(CalculateExamPaperAnswerCompleteEvent calculateExamPaperAnswerCompleteEvent) {
        Date now = new Date();

        ExamPaperAnswerInfo examPaperAnswerInfo = (ExamPaperAnswerInfo) calculateExamPaperAnswerCompleteEvent.getSource();
        ExamPaper examPaper = examPaperAnswerInfo.getExamPaper();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperAnswerInfo.getExamPaperQuestionCustomerAnswers();

        examPaperAnswerService.save(examPaperAnswer);
        examPaperQuestionCustomerAnswers.stream().filter(a -> QuestionTypeEnum.needSaveTextContent(a.getQuestionType())).forEach(d -> {
            ExamPaperContent textContent = new ExamPaperContent(d.getAnswer(), now);
            textContentService.save(textContent);
            d.setTextContentId(textContent.getId());
            d.setAnswer(null);
        });
        examPaperQuestionCustomerAnswers.forEach(d -> {
            d.setExamPaperAnswerId(examPaperAnswer.getId());
        });
        examPaperQuestionCustomerAnswerService.saveAll(examPaperQuestionCustomerAnswers);

        switch (ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            case Task: {
                examCustomerAnswerService.save(examPaper, examPaperAnswer, now);
                break;
            }
            default:
                break;
        }
    }
}
