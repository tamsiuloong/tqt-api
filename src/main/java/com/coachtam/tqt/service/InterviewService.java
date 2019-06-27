package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Interview;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:39
 */
public interface InterviewService {
    public Page<Interview> page(Integer pageNo, Integer pageSize);

    List<Interview> findAll();

    void save(Interview model);

    Interview findById(String id);

    void update(Interview model);

    void deleteByIds(String[] id);
}
