package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteItem;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	投票项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:59
 */
public interface VoteItemService {
    public Page<VoteItem> page(Integer pageNo, Integer pageSize);

    List<VoteItem> findAll();

    void save(VoteItem model);

    VoteItem findById(String id);

    void update(VoteItem model);

    void deleteByIds(String[] id);
}
