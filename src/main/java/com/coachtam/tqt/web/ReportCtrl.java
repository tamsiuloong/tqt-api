package com.coachtam.tqt.web;

import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.vo.EchartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-31 11:57
 * @Description: 报表
 */
@RestController
@RequestMapping("/api/report")
public class ReportCtrl {

    @Autowired
    private FeedbackService feedbackService;
    @GetMapping("/absorption")
    public EchartVO absorption()
    {
        EchartVO result = new EchartVO();
        List<Object[]> list = feedbackService.absorption();

        list.forEach(objects -> {
            result.getTitles().add((String) objects[0]);
            result.getValues().add((BigInteger) objects[1]);
        });
        return result;
    }
}
