package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.entity.KnowledgePoint;
import com.coachtam.tqt.respository.KnowledgePointDao;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    @Override
    public Page<KnowledgePoint> page(Integer pageNo,Integer pageSize)
    {
        return  knowledgePointDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<KnowledgePoint> findAll(String courseId) {
        TypedQuery<KnowledgePoint> query = entityManager.createQuery("SELECT c FROM KnowledgePoint c WHERE c.course.id = :id", KnowledgePoint.class);
        query.setParameter("id", courseId);
        return query.getResultList();
    }

    @Override
    public void save(KnowledgePoint bean) {
        knowledgePointDao.save(bean);
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
