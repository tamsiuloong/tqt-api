package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteRecord;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	投票记录
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:46
 */
public interface VoteRecordService {
    public Page<VoteRecord> page(Integer pageNo, Integer pageSize);

    List<VoteRecord> findAll();

    void save(VoteRecord model);

    VoteRecord findById(String id);

    void update(VoteRecord model);

    void deleteByIds(String[] id);

    Boolean findByVoteTopicId(Integer voteTopicId, String id);
}
