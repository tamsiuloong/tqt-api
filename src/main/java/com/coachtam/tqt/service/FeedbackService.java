package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.qo.FeedbackQO;
import com.coachtam.tqt.viewmodel.admin.EchartLineStackVM;
import com.coachtam.tqt.viewmodel.admin.EchartVM;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
/**
 * @Description:	学习反馈
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 17:28:52
 */
public interface FeedbackService {
    public Page<Feedback> page(Integer pageNo, Integer pageSize, String userId);
    public Page<Feedback> page(Integer pageNo, Integer pageSize, Specification<Feedback> specification);

    List<Feedback> findAll();

    void save(Feedback model);

    Feedback findById(String id);

    void update(Feedback model);

    void deleteByIds(String[] id);

    List<Object[]> absorption(FeedbackQO specification);

    /**
     * 查询未提交名单
     * @param specification
     * @param searchForm
     * @return
     */
    List<User> unCommitedList(Specification<Feedback> specification, FeedbackQO searchForm);


    EchartVM learnCurve(FeedbackQO searchForm);

    EchartLineStackVM learncurvepro(FeedbackQO searchForm);
}
