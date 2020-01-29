package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Interview;
import com.coachtam.tqt.qo.InterviewQO;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:39
 */
public interface InterviewService {
    public Page<Interview> page(Integer pageNo, Integer pageSize, InterviewQO searchForm, boolean all);

    List<Interview> findAll();

    void save(Interview model);

    Interview findById(Integer id);

    void update(Interview model);

    void deleteByIds(Integer[] id);
}
