package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.TextContent;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @Description:	试卷内容
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-5 16:39:37
 */
public interface TextContentService {
    public Page<TextContent> page(Integer pageNo, Integer pageSize);

    List<TextContent> findAll();

    void save(TextContent model);

    TextContent findById(Integer id);

    void update(TextContent model);

    void deleteByIds(Integer[] id);

    <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper);

    <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper);

}
