package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Feedback;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	学习反馈
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 17:28:52
 */
public interface FeedbackService {
    public Page<Feedback> page(Integer pageNo, Integer pageSize);

    List<Feedback> findAll();

    void save(Feedback model);

    Feedback findById(String id);

    void update(Feedback model);

    void deleteByIds(String[] id);

    List<Object[]> absorption();
}
