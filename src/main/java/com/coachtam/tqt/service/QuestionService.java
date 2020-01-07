package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.vo.admin.question.QuestionEditRequestVM;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
/**
 * @Description:	试卷试题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:46
 */
public interface QuestionService {
    public Page<Question> page(Integer pageNo, Integer pageSize, Specification<Question> specification);

    List<Question> findAll();

    void save(QuestionEditRequestVM model);

    Question findById(Integer id);

    void update(QuestionEditRequestVM model);

    void deleteByIds(Integer[] id);

    QuestionEditRequestVM getQuestionEditRequestVM(Question question);
}
