package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.entity.ExamPaperAnswerInfo;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.viewmodel.student.exam.ExamPaperSubmitVM;
import com.coachtam.tqt.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
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

    ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, User user);

    Page<ExamPaperAnswer> findByUserIdAndCourseId(ExamPaperAnswerPageVM model);

    /**
     * 试卷答题信息转成ViewModel 传给前台
     *
     * @param id 试卷id
     * @return ExamPaperSubmitVM
     */
    ExamPaperSubmitVM examPaperAnswerToVM(Integer id);

    /**
     * 试卷批改
     * @param examPaperSubmitVM  examPaperSubmitVM
     * @return String
     */
    String judge(ExamPaperSubmitVM examPaperSubmitVM);

    /**
     * 查询该试卷答卷
     * @param model
     * @return
     */
    Page<ExamPaperAnswer> findByExamPaperId(ExamPaperAnswerPageVM model);
}
