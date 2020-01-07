package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import com.coachtam.tqt.entity.other.ExamPaperAnswerUpdate;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitItemVM;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	学生考试答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:29:43
 */
public interface ExamPaperQuestionCustomerAnswerService {
    public Page<ExamPaperQuestionCustomerAnswer> page(Integer pageNo, Integer pageSize);

    List<ExamPaperQuestionCustomerAnswer> findAll();

    void save(ExamPaperQuestionCustomerAnswer model);

    void saveAll(List<ExamPaperQuestionCustomerAnswer> list);

    ExamPaperQuestionCustomerAnswer findById(Integer id);

    void update(ExamPaperQuestionCustomerAnswer model);

    void deleteByIds(Integer[] id);

    List<ExamPaperQuestionCustomerAnswer> findByExamPaperAnswerId(Integer id);

    public ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer qa);

    void updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);

}
