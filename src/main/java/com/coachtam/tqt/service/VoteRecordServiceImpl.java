package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.respository.VoteRecordDao;
import com.coachtam.tqt.service.VoteRecordService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	投票记录
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:46
 */
@Transactional
@Service
public class VoteRecordServiceImpl implements VoteRecordService {
    @Autowired
    private VoteRecordDao voteRecordDao;


    @Override
    public Page<VoteRecord> page(Integer pageNo,Integer pageSize)
    {
        return  voteRecordDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<VoteRecord> findAll() {
        return voteRecordDao.findAll();
    }

    @Override
    public void save(VoteRecord bean) {
        voteRecordDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            voteRecordDao.deleteById(id);
        }

    }

    @Override
    public void update(VoteRecord bean) {
        voteRecordDao.saveAndFlush(bean);
    }

    @Override
    public VoteRecord findById(String id) {
        return voteRecordDao.findById(id).get();
    }
}
