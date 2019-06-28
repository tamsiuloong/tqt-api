package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.respository.InterviewQuestionDao;
import com.coachtam.tqt.to.InterviewQuestionForm;
import com.coachtam.tqt.utils.PageUtils;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:48
 */
@Transactional
@Service
public class InterviewQuestionServiceImpl implements InterviewQuestionService {
    @Autowired
    private InterviewQuestionDao interviewQuestionDao;

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize, Integer interviewId)
    {
        //根据面试记录查询
        if(interviewId!=null)
        {
            InterviewQuestion interviewQuestion = new InterviewQuestion();
            Interview interview = new Interview();
            interview.setId(interviewId);
            interviewQuestion.setInterview(interview);
            Example<InterviewQuestion> example = Example.of(interviewQuestion);

            return interviewQuestionDao.findAll(example,PageUtils.of(pageNo,pageSize));
        }
        //否则查询所有
        return  interviewQuestionDao.findAll(PageUtils.of(pageNo,pageSize));
    }

    @Override
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize, InterviewQuestionForm searchForm) {


        return  interviewQuestionDao.findAll((root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(searchForm.getCourseId()!=null && !searchForm.getCourseId().isEmpty() &&!"all".equals(searchForm.getCourseId()))
            {
                Join<InterviewQuestion, Course> joins = root.join("course");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getCourseId());

                predicates.add(equal);
            }
            if(searchForm.getKnowledgePointId()!=null && !searchForm.getKnowledgePointId().isEmpty())
            {
                Join<InterviewQuestion, KnowledgePoint> joins = root.join("knowledgePoint");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getKnowledgePointId());

                predicates.add(equal);
            }
            if(searchForm.getTitle()!=null && !searchForm.getTitle().isEmpty())
            {
                Predicate equal = builder.like(root.get("title"), "%"+searchForm.getTitle()+"%");
                predicates.add(equal);
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        },PageUtils.of(pageNo,pageSize));
    }

    @Override
    public List<InterviewQuestion> findAll() {
        return interviewQuestionDao.findAll();
    }

    @Override
    public void save(InterviewQuestion bean) {
        interviewQuestionDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            interviewQuestionDao.deleteById(id);
        }

    }

    @Override
    public void update(InterviewQuestion bean) {
        interviewQuestionDao.saveAndFlush(bean);
    }

    @Override
    public InterviewQuestion findById(Integer id) {
        return interviewQuestionDao.findById(id).get();
    }
}
