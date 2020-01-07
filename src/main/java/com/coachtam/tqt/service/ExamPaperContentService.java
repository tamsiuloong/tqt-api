package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaperContent;
import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import com.coachtam.tqt.entity.task.TaskItemAnswerObject;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @Description:	试卷内容
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-5 16:39:37
 */
public interface ExamPaperContentService {
    public Page<ExamPaperContent> page(Integer pageNo, Integer pageSize);

    List<ExamPaperContent> findAll();

    void save(ExamPaperContent model);

    ExamPaperContent findById(Integer id);

    void update(ExamPaperContent model);

    void deleteByIds(Integer[] id);

    <T, R> ExamPaperContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper);

    <T, R> ExamPaperContent jsonConvertUpdate(ExamPaperContent textContent, List<T> list, Function<? super T, ? extends R> mapper);

}
