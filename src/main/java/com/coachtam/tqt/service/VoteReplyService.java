package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.entity.VoteReply;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	投票问题题目回复
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:29
 */
public interface VoteReplyService {
    Page<VoteReply> page(Integer pageNo, Integer pageSize);

    List<VoteReply> findAll();

    void save(List<VoteReply> beanList, VoteRecord voteRecord);

    VoteReply findById(String id);

    void update(VoteReply model);

    void deleteByIds(String[] id);
}
