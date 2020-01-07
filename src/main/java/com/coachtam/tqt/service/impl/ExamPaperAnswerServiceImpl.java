package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.config.utils.JsonUtil;
import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.entity.enums.ExamPaperAnswerStatusEnum;
import com.coachtam.tqt.entity.enums.ExamPaperTypeEnum;
import com.coachtam.tqt.entity.enums.QuestionTypeEnum;
import com.coachtam.tqt.entity.exam.ExamPaperTitleItemObject;
import com.coachtam.tqt.entity.other.ExamPaperAnswerUpdate;
import com.coachtam.tqt.entity.task.TaskItemAnswerObject;
import com.coachtam.tqt.respository.*;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.service.ExamPaperContentService;
import com.coachtam.tqt.service.ExamPaperQuestionCustomerAnswerService;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitItemVM;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitVM;
import com.coachtam.tqt.vo.student.exampaper.ExamPaperAnswerPageVM;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    private ExamPaperService examPaperService;

    @Autowired
    private ExamPaperContentService examPaperContentService;

    @Autowired
    private QuestionDao questionDao;



    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;


    @Autowired
    private TaskExamCustomerAnswerDao taskExamCustomerAnswerDao;

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
        ExamPaper examPaper = examPaperDao.findById(examPaperSubmitVM.getId()).get();
        ExamPaperTypeEnum paperTypeEnum = ExamPaperTypeEnum.fromCode(examPaper.getPaperType());
        if (paperTypeEnum == ExamPaperTypeEnum.Task) {
            ExamPaperAnswer examPaperAnswer = examPaperAnswerDao.getOne(examPaperSubmitVM.getId());
            if (null != examPaperAnswer)
            {return null;}
        }
        String frameTextContent = examPaperContentService.findById(examPaper.getId()).getContent();
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

    @Override
    @Transactional(readOnly = true)
    public Page<ExamPaperAnswer> findByUserIdAndCourseId(ExamPaperAnswerPageVM model) {
        Specification<ExamPaperAnswer> specification = (root, query, builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(StringUtils.isNotBlank(model.getCreateUser()))
            {
                Predicate equal = builder.equal(root.get("userId"), model.getCreateUser());
                predicates.add(equal);
            }

            if(StringUtils.isNotBlank(model.getCourseId()))
            {
                Predicate equal = builder.equal(root.get("courseId"), model.getCourseId());
                predicates.add(equal);
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return examPaperAnswerDao.findAll(specification,PageUtils.of(model.getPageIndex(), model.getPageSize()));
    }

    @Override
    public ExamPaperSubmitVM examPaperAnswerToVM(Integer id) {
        ExamPaperSubmitVM examPaperSubmitVM = new ExamPaperSubmitVM();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerDao.findById(id).get();
        examPaperSubmitVM.setId(examPaperAnswer.getId());
        examPaperSubmitVM.setDoTime(examPaperAnswer.getDoTime());
        examPaperSubmitVM.setScore(ExamUtil.scoreToVM(examPaperAnswer.getUserScore()));
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerService.findByExamPaperAnswerId(examPaperAnswer.getId());
        List<ExamPaperSubmitItemVM> examPaperSubmitItemVMS = examPaperQuestionCustomerAnswers.stream()
                .map(a -> examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(a))
                .collect(Collectors.toList());
        examPaperSubmitVM.setAnswerItems(examPaperSubmitItemVMS);
        return examPaperSubmitVM;
    }
    @Override
    @Transactional
    public String judge(ExamPaperSubmitVM examPaperSubmitVM) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerDao.findById(examPaperSubmitVM.getId()).get();
        List<ExamPaperSubmitItemVM> judgeItems = examPaperSubmitVM.getAnswerItems().stream().filter(d -> d.getDoRight() == null).collect(Collectors.toList());
        List<ExamPaperAnswerUpdate> examPaperAnswerUpdates = new ArrayList<>(judgeItems.size());
        Integer customerScore = examPaperAnswer.getUserScore();
        Integer questionCorrect = examPaperAnswer.getQuestionCorrect();
        for (ExamPaperSubmitItemVM d : judgeItems) {
            ExamPaperAnswerUpdate examPaperAnswerUpdate = new ExamPaperAnswerUpdate();
            examPaperAnswerUpdate.setId(d.getId());
            examPaperAnswerUpdate.setCustomerScore(ExamUtil.scoreFromVM(d.getScore()));
            boolean doRight = examPaperAnswerUpdate.getCustomerScore().equals(ExamUtil.scoreFromVM(d.getQuestionScore()));
            examPaperAnswerUpdate.setDoRight(doRight);
            examPaperAnswerUpdates.add(examPaperAnswerUpdate);
            customerScore += examPaperAnswerUpdate.getCustomerScore();
            if (examPaperAnswerUpdate.getDoRight()) {
                ++questionCorrect;
            }
        }
        examPaperAnswer.setUserScore(customerScore);
        examPaperAnswer.setQuestionCorrect(questionCorrect);
        examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        examPaperAnswerDao.save(examPaperAnswer);
        examPaperQuestionCustomerAnswerService.updateScore(examPaperAnswerUpdates);

        ExamPaperTypeEnum examPaperTypeEnum = ExamPaperTypeEnum.fromCode(examPaperAnswer.getPaperType());
        switch (examPaperTypeEnum) {
            case Task:
                ExamPaper examPaper = examPaperDao.findById(examPaperAnswer.getExamPaperId()).get();
                Integer taskId = examPaper.getTaskExamId();
                String userId = examPaperAnswer.getUserId();
                TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerDao.getBytaskExamIdAndUserId(taskId, userId);
                ExamPaperContent textContent = examPaperContentService.findById(taskExamCustomerAnswer.getTextContentId());
                List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtils.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
                taskItemAnswerObjects.stream()
                        .filter(d -> d.getExamPaperAnswerId() .equals(examPaperAnswer.getId()) )
                        .findFirst().ifPresent(taskItemAnswerObject -> taskItemAnswerObject.setStatus(examPaperAnswer.getStatus()));
                examPaperContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
                examPaperContentService.update(textContent);
                break;
            default:
                break;
        }
        return ExamUtil.scoreToVM(customerScore);
    }
}
