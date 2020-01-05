package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.QuestionItems;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	试卷试题选项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:31:19
 */
public interface QuestionItemsService {
    public Page<QuestionItems> page(Integer pageNo, Integer pageSize);

    List<QuestionItems> findAll();

    void save(QuestionItems model);

    QuestionItems findById(Integer id);

    void update(QuestionItems model);

    void deleteByIds(Integer[] id);
}
