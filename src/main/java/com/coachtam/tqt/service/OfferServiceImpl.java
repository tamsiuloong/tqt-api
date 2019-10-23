package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Offer;
import com.coachtam.tqt.respository.OfferDao;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	入职邀请
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-10-23 9:45:45
 */
@Transactional
@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferDao offerDao;


    @Override
    public Page<Offer> page(Integer pageNo,Integer pageSize)
    {
        return  offerDao.findAll(PageUtils.of(pageNo,pageSize));
    }

    @Override
    public List<Offer> findListByUid(String uid) {
        return  offerDao.findAllByUserId(uid);
    }

    @Override
    public List<Offer> findAll() {
        return offerDao.findAll();
    }

    @Override
    public void save(Offer bean) {
        offerDao.save(bean);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id:ids) {
            offerDao.deleteById(id);
        }

    }

    @Override
    public void update(Offer bean) {
        offerDao.saveAndFlush(bean);
    }

    @Override
    public Offer findById(Long id) {
        return offerDao.findById(id).get();
    }
}
