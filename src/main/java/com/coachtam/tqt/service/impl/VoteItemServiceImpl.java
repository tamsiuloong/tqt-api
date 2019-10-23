package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.VoteItem;
import com.coachtam.tqt.respository.VoteItemDao;
import com.coachtam.tqt.service.VoteItemService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	投票项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:59
 */
@Transactional
@Service
public class VoteItemServiceImpl implements VoteItemService {
    @Autowired
    private VoteItemDao voteItemDao;


    @Override
    public Page<VoteItem> page(Integer pageNo,Integer pageSize)
    {
        return  voteItemDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<VoteItem> findAll() {
        return voteItemDao.findAll();
    }

    @Override
    public void save(VoteItem bean) {
        voteItemDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            voteItemDao.deleteById(id);
        }

    }

    @Override
    public void update(VoteItem bean) {
        voteItemDao.saveAndFlush(bean);
    }

    @Override
    public VoteItem findById(String id) {
        return voteItemDao.findById(id).get();
    }
}
