package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:17
 */
public interface ExamPaperAnswerService {
    public Page<ExamPaperAnswer> page(Integer pageNo, Integer pageSize);

    List<ExamPaperAnswer> findAll();

    void save(ExamPaperAnswer model);

    ExamPaperAnswer findById(Integer id);

    void update(ExamPaperAnswer model);

    void deleteByIds(Integer[] id);
}
