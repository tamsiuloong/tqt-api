package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.entity.InterviewQuestion;
import com.coachtam.tqt.entity.KnowledgePoint;
import com.coachtam.tqt.respository.KnowledgePointDao;
import com.coachtam.tqt.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:	知识点
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-28 13:55:31
 */
@Transactional
@Service
public class KnowledgePointServiceImpl implements KnowledgePointService {
    @Autowired
    private KnowledgePointDao knowledgePointDao;
    @PersistenceContext
    private EntityManager entityManager;

    private final static String SPLIT_KEY = ",";
    @Override
    public Page<KnowledgePoint> page(Integer pageNo, Integer pageSize, String keyWord)
    {

        return  knowledgePointDao.findAll((root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(StringUtils.isNotBlank(keyWord))
            {
                Predicate equal = builder.like(root.get("name"), "%"+keyWord+"%");
                predicates.add(equal);
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        },PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<KnowledgePoint> findAll(String courseId) {
        TypedQuery<KnowledgePoint> query = entityManager.createQuery("SELECT c FROM KnowledgePoint c WHERE c.course.id = :id", KnowledgePoint.class);
        query.setParameter("id", courseId);
        return query.getResultList();
    }

    @Override
    public void save(KnowledgePoint bean) {
        //判断是不是批量导入
        String name = bean.getName().replaceAll("，",SPLIT_KEY);
        if(bean!=null&& StringUtils.isNotBlank(name))
        {
            if (name.contains(SPLIT_KEY)) {
                String[] names = name.split(SPLIT_KEY);
                if(names!=null&& names.length>0)
                {
                    List<KnowledgePoint> knowledgePointList = new ArrayList<>(names.length);
                    for(String n:names)
                    {
                        KnowledgePoint k = new KnowledgePoint();
                        k.setCourse(bean.getCourse());
                        k.setName(n);
                        knowledgePointList.add(k);
                    }

                    knowledgePointDao.saveAll(knowledgePointList);
                }

            }
        }
        else {
            knowledgePointDao.save(bean);
        }

    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            knowledgePointDao.deleteById(id);
        }

    }

    @Override
    public void update(KnowledgePoint bean) {
        knowledgePointDao.saveAndFlush(bean);
    }

    @Override
    public KnowledgePoint findById(Integer id) {
        return knowledgePointDao.findById(id).get();
    }
}
