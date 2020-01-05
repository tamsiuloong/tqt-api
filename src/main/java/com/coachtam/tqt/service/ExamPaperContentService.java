package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaperContent;
import org.springframework.data.domain.Page;
import java.util.List;
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
}
