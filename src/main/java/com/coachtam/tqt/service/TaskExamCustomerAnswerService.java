package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.entity.TaskExamCustomerAnswer;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
/**
 * @Description:	任务试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-7 21:49:38
 */
public interface TaskExamCustomerAnswerService {
    public Page<TaskExamCustomerAnswer> page(Integer pageNo, Integer pageSize);

    List<TaskExamCustomerAnswer> findAll();

    void save(TaskExamCustomerAnswer model);

    TaskExamCustomerAnswer findById(Integer id);

    void update(TaskExamCustomerAnswer model);

    void deleteByIds(Integer[] id);

    void save(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer, Date now);
}
