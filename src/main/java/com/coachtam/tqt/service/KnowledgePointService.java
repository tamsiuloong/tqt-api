package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.KnowledgePoint;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	知识点
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-28 13:55:31
 */
public interface KnowledgePointService {
    public Page<KnowledgePoint> page(Integer pageNo, Integer pageSize, String keyWord);

    List<KnowledgePoint> findAll(String courseId);

    void save(KnowledgePoint model);

    KnowledgePoint findById(Integer id);

    void update(KnowledgePoint model);

    void deleteByIds(Integer[] id);
}
