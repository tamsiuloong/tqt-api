package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.InterviewQuestion;
import com.coachtam.tqt.qo.BatchInterviewQuestionQO;
import com.coachtam.tqt.qo.InterviewQuestionQO;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:48
 */
public interface InterviewQuestionService {
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize, Integer interviewId);
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize, InterviewQuestionQO searchForm);

    List<InterviewQuestion> findAll();

    void save(InterviewQuestion model);

    InterviewQuestion findById(Integer id);

    void update(InterviewQuestion model);

    void deleteByIds(Integer[] id);


    /**
     * 批量导入面试题
     */
    void batchImport(BatchInterviewQuestionQO form);


    /**
     * 将world转table
     * @param text
     * @return
     */
    List<InterviewQuestion> parseWord(String text);
}
