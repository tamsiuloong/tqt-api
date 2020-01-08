package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperPageRequestVM;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
/**
 * @Description:	试卷
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:28:32
 */
public interface ExamPaperService {
    public Page<ExamPaper> page(Integer pageNo, Integer pageSize, Specification<ExamPaper> specification);

    List<ExamPaper> findAll();

    void save(ExamPaperEditRequestVM model);

    ExamPaper findById(Integer id);

    void update(ExamPaperEditRequestVM model);

    void deleteByIds(Integer[] id);

    Page page(ExamPaperPageRequestVM model);


    ExamPaperEditRequestVM examPaperToVM(Integer id);

    List<ExamPaper> findPaperByType(int type);
}
