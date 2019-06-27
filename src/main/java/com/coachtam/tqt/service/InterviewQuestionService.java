package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.InterviewQuestion;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:48
 */
public interface InterviewQuestionService {
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize);

    List<InterviewQuestion> findAll();

    void save(InterviewQuestion model);

    InterviewQuestion findById(String id);

    void update(InterviewQuestion model);

    void deleteByIds(String[] id);
}
