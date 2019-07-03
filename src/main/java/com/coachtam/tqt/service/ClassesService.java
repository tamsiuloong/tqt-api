package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Classes;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-31 16:56:55
 */
public interface ClassesService {
    public Page<Classes> page(Integer pageNo, Integer pageSize);

    List<Classes> findAll();

    void save(Classes model);

    Classes findById(String id);

    void update(Classes model);

    void deleteByIds(String[] id);

    List<Classes> findAllByClosed(Boolean closed);
}
