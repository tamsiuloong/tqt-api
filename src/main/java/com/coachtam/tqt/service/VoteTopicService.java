package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteTopic;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	投票主题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:50:38
 */
public interface VoteTopicService {
    public Page<VoteTopic> page(Integer pageNo, Integer pageSize);

    List<VoteTopic> findAll();

    void save(VoteTopic model);

    VoteTopic findById(String id);

    void update(VoteTopic model);

    void deleteByIds(String[] id);
}
