package com.coachtam.tqt.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-30 13:40
 * @Description:
 */
public class PageUtils {
    public static Pageable of(Integer pageNo, Integer pageSize) {
        return PageRequest.of(pageNo-1,pageSize);
    }

    public static Pageable of(Integer pageNo, Integer pageSize, Sort sort) {
        return PageRequest.of(pageNo-1,pageSize,sort);
    }
}
