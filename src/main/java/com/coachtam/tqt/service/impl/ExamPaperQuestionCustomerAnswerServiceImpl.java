package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.TextContent;
import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import com.coachtam.tqt.entity.enums.QuestionTypeEnum;
import com.coachtam.tqt.entity.other.ExamPaperAnswerUpdate;
import com.coachtam.tqt.respository.ExamPaperQuestionCustomerAnswerDao;
import com.coachtam.tqt.service.TextContentService;
import com.coachtam.tqt.service.ExamPaperQuestionCustomerAnswerService;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitItemVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	学生考试答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:29:43
 */
@Transactional
@Service
public class ExamPaperQuestionCustomerAnswerServiceImpl implements ExamPaperQuestionCustomerAnswerService {
    @Autowired
    private ExamPaperQuestionCustomerAnswerDao examPaperQuestionCustomerAnswerDao;

    @Autowired
    private TextContentService textContentService;

    @Override
    public Page<ExamPaperQuestionCustomerAnswer> page(Integer pageNo,Integer pageSize)
    {
        return  examPaperQuestionCustomerAnswerDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<ExamPaperQuestionCustomerAnswer> findAll() {
        return examPaperQuestionCustomerAnswerDao.findAll();
    }

    @Override
    public void save(ExamPaperQuestionCustomerAnswer bean) {
        examPaperQuestionCustomerAnswerDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            examPaperQuestionCustomerAnswerDao.deleteById(id);
        }

    }

    @Override
    public void update(ExamPaperQuestionCustomerAnswer bean) {
        examPaperQuestionCustomerAnswerDao.saveAndFlush(bean);
    }

    @Override
    public ExamPaperQuestionCustomerAnswer findById(Integer id) {
        return examPaperQuestionCustomerAnswerDao.findById(id).get();
    }

    @Override
    public void saveAll(List<ExamPaperQuestionCustomerAnswer> list) {
        examPaperQuestionCustomerAnswerDao.saveAll(list);
    }

    @Override
    public ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer qa) {
        ExamPaperSubmitItemVM examPaperSubmitItemVM = new ExamPaperSubmitItemVM();
        examPaperSubmitItemVM.setId(qa.getId());
        examPaperSubmitItemVM.setQuestionId(qa.getQuestionId());
        examPaperSubmitItemVM.setDoRight(qa.getDoRight());
        examPaperSubmitItemVM.setItemOrder(qa.getItemOrder());
        examPaperSubmitItemVM.setQuestionScore(ExamUtil.scoreToVM(qa.getQuestionScore()));
        examPaperSubmitItemVM.setScore(ExamUtil.scoreToVM(qa.getCustomerScore()));
        setSpecialToVM(examPaperSubmitItemVM, qa);
        return examPaperSubmitItemVM;
    }

    private void setSpecialToVM(ExamPaperSubmitItemVM examPaperSubmitItemVM, ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case MultipleChoice:
                examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                examPaperSubmitItemVM.setContentArray(ExamUtil.contentToArray(examPaperQuestionCustomerAnswer.getAnswer()));
                break;
            case GapFilling:
                TextContent textContent = textContentService.findById(examPaperQuestionCustomerAnswer.getTextContentId());
                List<String> correctAnswer = JsonUtils.toJsonListObject(textContent.getContent(), String.class);
                examPaperSubmitItemVM.setContentArray(correctAnswer);
                break;
            default:
                if (QuestionTypeEnum.needSaveTextContent(examPaperQuestionCustomerAnswer.getQuestionType())) {
                    TextContent content = textContentService.findById(examPaperQuestionCustomerAnswer.getTextContentId());
                    examPaperSubmitItemVM.setContent(content.getContent());
                } else {
                    examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                }
                break;
        }
    }

    @Override
    public List<ExamPaperQuestionCustomerAnswer> findByExamPaperAnswerId(Integer id) {
        return examPaperQuestionCustomerAnswerDao.findByExamPaperAnswerId(id);
    }

    public ExamPaperQuestionCustomerAnswerServiceImpl() {
    }

    @Override
    public void updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates) {
        if(examPaperAnswerUpdates!=null)
        {
            examPaperAnswerUpdates.forEach(e->
                examPaperQuestionCustomerAnswerDao.updateScore(e.getCustomerScore(),e.getDoRight(),e.getId())
            );
        }
    }
}
