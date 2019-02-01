package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Module;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 13:54:18
 */
public interface ModuleService {
    public Page<Module> page(Integer pageNo, Integer pageSize);

    List<Module> findAll();

    void save(Module model);

    Module findById(String id);

    void update(Module model);

    void deleteByIds(String[] id);
}
