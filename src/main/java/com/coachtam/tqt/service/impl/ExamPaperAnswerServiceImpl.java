package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.config.utils.JsonUtil;
import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.entity.enums.ExamPaperAnswerStatusEnum;
import com.coachtam.tqt.entity.enums.ExamPaperTypeEnum;
import com.coachtam.tqt.entity.enums.QuestionTypeEnum;
import com.coachtam.tqt.entity.exam.ExamPaperTitleItemObject;
import com.coachtam.tqt.respository.ExamPaperAnswerDao;
import com.coachtam.tqt.respository.ExamPaperContentDao;
import com.coachtam.tqt.respository.ExamPaperDao;
import com.coachtam.tqt.respository.QuestionDao;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitItemVM;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:	试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:17
 */
@Transactional
@Service
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {
    @Autowired
    private ExamPaperAnswerDao examPaperAnswerDao;

    @Autowired
    private ExamPaperDao examPaperDao;

    @Autowired
    private ExamPaperContentDao examPaperContentDao;

    @Autowired
    private QuestionDao questionDao;

    @Override
    public Page<ExamPaperAnswer> page(Integer pageNo,Integer pageSize)
    {
        return  examPaperAnswerDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<ExamPaperAnswer> findAll() {
        return examPaperAnswerDao.findAll();
    }

    @Override
    public void save(ExamPaperAnswer bean) {
        examPaperAnswerDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            examPaperAnswerDao.deleteById(id);
        }

    }

    @Override
    public void update(ExamPaperAnswer bean) {
        examPaperAnswerDao.saveAndFlush(bean);
    }

    @Override
    public ExamPaperAnswer findById(Integer id) {
        return examPaperAnswerDao.findById(id).get();
    }

    @Override
    public ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, User user) {
        ExamPaperAnswerInfo examPaperAnswerInfo = new ExamPaperAnswerInfo();
        Date now = new Date();
        ExamPaper examPaper = examPaperDao.getOne(examPaperSubmitVM.getId());
        ExamPaperTypeEnum paperTypeEnum = ExamPaperTypeEnum.fromCode(examPaper.getPaperType());
        if (paperTypeEnum == ExamPaperTypeEnum.Task) {
            ExamPaperAnswer examPaperAnswer = examPaperAnswerDao.getOne(examPaperSubmitVM.getId());
            if (null != examPaperAnswer)
            {return null;}
        }
        String frameTextContent = examPaperContentDao.getOne(examPaper.getId()).getContent();
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtils.toJsonListObject(frameTextContent, ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream().flatMap(t -> t.getQuestionItems().stream().map(q -> q.getId())).collect(Collectors.toList());
        List<Question> questions = questionDao.findByIdIn(questionIds);
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> {
                            Question question = questions.stream().filter(tq -> tq.getId().equals(q.getId())).findFirst().get();
                            ExamPaperSubmitItemVM customerQuestionAnswer = examPaperSubmitVM.getAnswerItems().stream()
                                    .filter(tq -> tq.getQuestionId().equals(q.getId()))
                                    .findFirst()
                                    .orElse(null);
                            return ExamPaperQuestionCustomerAnswerFromVM(question, customerQuestionAnswer, examPaper, q.getItemOrder(), user, now);
                        })
                ).collect(Collectors.toList());

        ExamPaperAnswer examPaperAnswer = ExamPaperAnswerFromVM(examPaperSubmitVM, examPaper, examPaperQuestionCustomerAnswers, user, now);
        examPaperAnswerInfo.setExamPaper(examPaper);
        examPaperAnswerInfo.setExamPaperAnswer(examPaperAnswer);
        examPaperAnswerInfo.setExamPaperQuestionCustomerAnswers(examPaperQuestionCustomerAnswers);
        return examPaperAnswerInfo;
    }

    private ExamPaperQuestionCustomerAnswer ExamPaperQuestionCustomerAnswerFromVM(Question question, ExamPaperSubmitItemVM customerQuestionAnswer, ExamPaper examPaper, Integer itemOrder, User user, Date now) {
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = new ExamPaperQuestionCustomerAnswer();
        examPaperQuestionCustomerAnswer.setQuestionId(question.getId());
        examPaperQuestionCustomerAnswer.setExamPaperId(examPaper.getId());
        examPaperQuestionCustomerAnswer.setQuestionScore(question.getScore());
        examPaperQuestionCustomerAnswer.setCourseId(examPaper.getCourse().getId());
        examPaperQuestionCustomerAnswer.setItemOrder(itemOrder);
        examPaperQuestionCustomerAnswer.setCreateTime(now);
        examPaperQuestionCustomerAnswer.setUserId(user.getId());
        examPaperQuestionCustomerAnswer.setQuestionType(question.getQuestionType());
        examPaperQuestionCustomerAnswer.setQuestionTextContentId(question.getId());
        if (null == customerQuestionAnswer) {
            examPaperQuestionCustomerAnswer.setCustomerScore(0);
        } else {
            setSpecialFromVM(examPaperQuestionCustomerAnswer, question, customerQuestionAnswer);
        }
        return examPaperQuestionCustomerAnswer;
    }

    private ExamPaperAnswer ExamPaperAnswerFromVM(ExamPaperSubmitVM examPaperSubmitVM, ExamPaper examPaper, List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers, User user, Date now) {
        Integer systemScore = examPaperQuestionCustomerAnswers.stream().mapToInt(a -> a.getCustomerScore()).sum();
        long questionCorrect = examPaperQuestionCustomerAnswers.stream().filter(a -> a.getCustomerScore().equals(a.getQuestionScore())).count();
        ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
        examPaperAnswer.setPaperName(examPaper.getName());
        examPaperAnswer.setDoTime(examPaperSubmitVM.getDoTime());
        examPaperAnswer.setExamPaperId(examPaper.getId());
        examPaperAnswer.setUserId(user.getId());
        examPaperAnswer.setCreateTime(now);
        examPaperAnswer.setCourseId(examPaper.getCourse().getId());
        examPaperAnswer.setQuestionCount(examPaper.getQuestionCount());
        examPaperAnswer.setPaperScore(examPaper.getScore());
        examPaperAnswer.setPaperType(examPaper.getPaperType());
        examPaperAnswer.setSystemScore(systemScore);
        examPaperAnswer.setUserScore(systemScore);
        examPaperAnswer.setTaskExamId(examPaper.getTaskExamId());
        examPaperAnswer.setQuestionCorrect((int) questionCorrect);
        boolean needJudge = examPaperQuestionCustomerAnswers.stream().anyMatch(d -> QuestionTypeEnum.needSaveTextContent(d.getQuestionType()));
        if (needJudge) {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        } else {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        }
        return examPaperAnswer;
    }
    private void setSpecialFromVM(ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer, Question question, ExamPaperSubmitItemVM customerQuestionAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getContent()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case MultipleChoice:
                String customerAnswer = ExamUtil.contentToString(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(customerAnswer);
                examPaperQuestionCustomerAnswer.setDoRight(customerAnswer.equals(question.getCorrect()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case GapFilling:
                String correctAnswer = JsonUtils.toJsonStr(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(correctAnswer);
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
            default:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
        }
    }
}
