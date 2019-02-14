package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.UserInfo;
import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.to.FeedbackForm;
import com.coachtam.tqt.vo.EchartVO;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
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
    @PostMapping("/absorption")
    public EchartVO absorption(@RequestBody FeedbackForm searchForm)
    {

        EchartVO result = new EchartVO();
        List<Object[]> list = feedbackService.absorption(searchForm);

        list.forEach(objects -> {
            result.getTitles().add((String) objects[0]);
            result.getValues().add((Long) objects[1]);
        });
        return result;
    }
}
