package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteSubtopic;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	投票子题目
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:01
 */
public interface VoteSubtopicService {
    public Page<VoteSubtopic> page(Integer pageNo, Integer pageSize);

    List<VoteSubtopic> findAll();

    void save(VoteSubtopic model);

    VoteSubtopic findById(String id);

    void update(VoteSubtopic model);

    void deleteByIds(String[] id);

    List<VoteSubtopic> findAllByVotetopicId(Integer votetopicId);
}
