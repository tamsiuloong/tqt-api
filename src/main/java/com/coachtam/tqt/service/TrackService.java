package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-14 15:41:24
 */
public interface TrackService {
    public Page<Track> page(Integer pageNo, Integer pageSize, Specification<Track> specification);

    List<Track> findAll();

    void save(Track model);

    Track findById(String id);

    void update(Track model);

    void deleteByIds(String[] id);
}
