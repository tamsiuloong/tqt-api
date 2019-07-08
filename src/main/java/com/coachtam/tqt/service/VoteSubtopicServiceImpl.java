package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteSubtopic;
import com.coachtam.tqt.respository.VoteSubtopicDao;
import com.coachtam.tqt.service.VoteSubtopicService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	投票子题目
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:01
 */
@Transactional
@Service
public class VoteSubtopicServiceImpl implements VoteSubtopicService {
    @Autowired
    private VoteSubtopicDao voteSubtopicDao;


    @Override
    public Page<VoteSubtopic> page(Integer pageNo,Integer pageSize)
    {
        return  voteSubtopicDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<VoteSubtopic> findAll() {
        return voteSubtopicDao.findAll();
    }

    @Override
    public void save(VoteSubtopic bean) {
        voteSubtopicDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            voteSubtopicDao.deleteById(id);
        }

    }

    @Override
    public void update(VoteSubtopic bean) {
        voteSubtopicDao.saveAndFlush(bean);
    }

    @Override
    public VoteSubtopic findById(String id) {
        return voteSubtopicDao.findById(id).get();
    }
}
