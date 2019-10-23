package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.Track;
import com.coachtam.tqt.respository.TrackDao;
import com.coachtam.tqt.service.TrackService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-14 15:41:24
 */
@Transactional
@Service
public class TrackServiceImpl implements TrackService {
    @Autowired
    private TrackDao trackDao;


    @Override
    public Page<Track> page(Integer pageNo,Integer pageSize, Specification<Track> specification)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return  trackDao.findAll(specification,PageUtils.of(pageNo,pageSize,sort));
    }



    @Override
    public List<Track> findAll() {
        return trackDao.findAll();
    }

    @Override
    public void save(Track bean) {
        trackDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            trackDao.deleteById(id);
        }

    }

    @Override
    public void update(Track bean) {
        trackDao.saveAndFlush(bean);
    }

    @Override
    public Track findById(String id) {
        return trackDao.findById(id).get();
    }
}
