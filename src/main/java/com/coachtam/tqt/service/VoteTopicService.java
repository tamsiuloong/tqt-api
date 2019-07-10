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
    /**
     * 是否所有的
     * @param pageNo
     * @param pageSize
     * @param isAll true:所有   false:只查自己班的
     * @return
     */
    public Page<VoteTopic> page(Integer pageNo, Integer pageSize, boolean isAll);

    List<VoteTopic> findAll();

    void save(VoteTopic model);

    VoteTopic findById(Integer id);

    void update(VoteTopic model);

    void deleteByIds(Integer[] id);
}
