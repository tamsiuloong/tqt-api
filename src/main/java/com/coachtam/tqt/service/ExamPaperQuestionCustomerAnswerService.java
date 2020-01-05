package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
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

    ExamPaperQuestionCustomerAnswer findById(Integer id);

    void update(ExamPaperQuestionCustomerAnswer model);

    void deleteByIds(Integer[] id);
}
