package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Leave;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	请假
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-5 22:48:29
 */
public interface LeaveService {
    public Page<Leave> page(Integer pageNo, Integer pageSize);

    List<Leave> findAll();

    void save(Leave model);

    Leave findById(String id);

    void update(Leave model);

    void deleteByIds(String[] id);
}
