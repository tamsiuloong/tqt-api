package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.TextContent;
import com.coachtam.tqt.respository.TextContentDao;
import com.coachtam.tqt.service.TextContentService;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description:	试卷内容
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-5 16:39:37
 */
@Transactional
@Service
public class TextContentServiceImpl implements TextContentService {
    @Autowired
    private TextContentDao textContentDao;


    @Override
    public Page<TextContent> page(Integer pageNo, Integer pageSize)
    {
        return  textContentDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<TextContent> findAll() {
        return textContentDao.findAll();
    }

    @Override
    public void save(TextContent bean) {
        textContentDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            textContentDao.deleteById(id);
        }

    }

    @Override
    public void update(TextContent bean) {
        textContentDao.saveAndFlush(bean);
    }

    @Override
    public TextContent findById(Integer id) {
        return textContentDao.findById(id).get();
    }


    @Override
    public <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtils.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtils.toJsonStr(mapList);
        }
        TextContent textContent = new TextContent(frameTextContent, now);
        //insertByFilter(textContent);  cache useless
        return textContent;
    }

    @Override
    public <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtils.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtils.toJsonStr(mapList);
        }
        textContent.setContent(frameTextContent);
        //this.updateByIdFilter(textContent);  cache useless
        return textContent;
    }


}
