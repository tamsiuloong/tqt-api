package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.config.security.UserDetail;
import com.coachtam.tqt.config.utils.JsonUtil;
import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.entity.QuestionItems;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.enums.QuestionTypeEnum;
import com.coachtam.tqt.respository.QuestionDao;
import com.coachtam.tqt.respository.QuestionItemsDao;
import com.coachtam.tqt.service.QuestionService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.viewmodel.admin.question.QuestionEditItemVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionEditRequestVM;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.coachtam.tqt.service.impl.ExamPaperServiceImpl.modelMapper;

/**
 * @Description:	试卷试题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:46
 */
@Transactional
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionItemsDao questionItemsDao;
    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Page<Question> page(Integer pageNo, Integer pageSize, Specification<Question> specification)
    {
        return  questionDao.findAll(specification,PageUtils.of(pageNo,pageSize, Sort.by(Sort.Direction.DESC,"createTime")));
    }



    @Override
    public List<Question> findAll() {
        return questionDao.findAll();
    }

    @Override
    public void save(QuestionEditRequestVM form)  {

        Question bean = new Question();
        BeanUtils.copyProperties(form,bean);
        bean.setDeleted(false);
        if(form.getQuestionType().equals(2))
        {
            bean.setCorrect(StringUtils.join(form.getCorrectArray(),","));
        }
        bean.setCreateTime(new Date());

        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currUser = userService.findByUsername(user.getUsername());

        bean.setUser(currUser);



        List<QuestionEditItemVM> items = form.getItems();

        String json = JsonUtil.toJson(items);
        QuestionItems questionItems = new QuestionItems();
        questionItems.setContent(json);
        questionItems.setId(bean.getId());
//            questionItems.setCreateTime(bean.getCreateTime());
//            questionItemsDao.save(questionItems);

        bean.setQuestionItems(questionItems);
        questionItems.setQuestion(bean);

        questionDao.save(bean);

    }

    @Override
    public void deleteByIds(Integer[] ids) {
        //逻辑删除
        for (Integer id:ids) {
            Question question = questionDao.getOne(id);
            question.setDeleted(true);
        }

    }

    @Override
    public void update(QuestionEditRequestVM form) {

        Question bean = questionDao.getOne(form.getId());
        BeanUtils.copyProperties(form,bean);
        bean.setCreateTime(new Date());
        if(form.getQuestionType().equals(QuestionTypeEnum.MultipleChoice.getCode()))
        {
            bean.setCorrect(JsonUtils.toJson(form.getCorrectArray()));
        }
        questionDao.save(bean);

        List<QuestionEditItemVM> items = form.getItems();
        String json = JsonUtil.toJson(items);
        QuestionItems questionItems = bean.getQuestionItems();
        questionItems.setContent(json);
        questionItemsDao.save(questionItems);

    }

    @Override
    public Question findById(Integer id) {
        return questionDao.findById(id).get();
    }

    @Override
    public QuestionEditRequestVM getQuestionEditRequestVM(Question question) {
        //题目映射
//        QuestionItems questionInfoTextContent = questionItemsDao.getOne(question.getId());
//        QuestionObject questionObject = JsonUtils.toJsonObject(questionInfoTextContent.getContent(), QuestionObject.class);
        QuestionEditRequestVM questionEditRequestVM = modelMapper.map(question, QuestionEditRequestVM.class);
        questionEditRequestVM.setTitle(question.getTitle());

        //答案
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                questionEditRequestVM.setCorrect(question.getCorrect());
                break;
            case MultipleChoice:
                questionEditRequestVM.setCorrectArray(ExamUtil.contentToArray(question.getCorrect()));
                break;
            case GapFilling:
                try {
                    List<Map<String,Object>> questionItems = objectMapper.readValue(question.getQuestionItems().getContent(),new TypeReference<List<Map<String,Object>>>(){});
                    List<String> correctContent = questionItems.stream().map(d -> d.get("content").toString()).collect(Collectors.toList());
                    questionEditRequestVM.setCorrectArray(correctContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case ShortAnswer:
                questionEditRequestVM.setCorrect(question.getCorrect());
                break;
            default:
                break;
        }
        questionEditRequestVM.setScore(ExamUtil.scoreToIntegerVM(question.getScore()));
        questionEditRequestVM.setAnalyze(question.getAnalyze());


        //题目项映射
        List<Map<String,Object>> questionItems = null;
        try {
            questionItems = objectMapper.readValue(question.getQuestionItems().getContent(),new TypeReference<List<Map<String,Object>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<QuestionEditItemVM> editItems =questionItems.stream().map(o -> {
            QuestionEditItemVM questionEditItemVM = modelMapper.map(o, QuestionEditItemVM.class);
            Integer score = (Integer) o.get("score");
            if (o.get("score") != null) {
                questionEditItemVM.setScore(ExamUtil.scoreToVM(score));
            }
            return questionEditItemVM;
        }).collect(Collectors.toList());
        questionEditRequestVM.setItems(editItems);
        return questionEditRequestVM;
    }

    @Override
    public QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId) {
        //题目映射
        Question question = questionDao.findById(questionId).get();
        return getQuestionEditRequestVM(question);
    }
}
