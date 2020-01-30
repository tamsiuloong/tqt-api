package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.qo.FeedbackQO;
import com.coachtam.tqt.viewmodel.admin.EchartLineStackVM;
import com.coachtam.tqt.viewmodel.admin.EchartVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public EchartVM absorption(@RequestBody FeedbackQO searchForm)
    {

        EchartVM result = new EchartVM();
        List<Object[]> list = feedbackService.absorption(searchForm);

        list.forEach(objects -> {
            result.getTitles().add((String) objects[0]);
            result.getValues().add((Long) objects[1]);
        });
        return result;
    }


    /**
     * 整体学习曲线
     * @param searchForm
     * @return
     */
    @PostMapping("/learncurve")
    public EchartVM learncurve(@RequestBody FeedbackQO searchForm)
    {

        EchartVM result = feedbackService.learnCurve(searchForm);

        return result;
    }
    /**
     * 个人学习曲线
     * @param searchForm
     * @return
     */
    @PostMapping("/learncurvepro")
    public EchartLineStackVM learncurvepro(@RequestBody FeedbackQO searchForm)
    {

        EchartLineStackVM result = feedbackService.learncurvepro(searchForm);

        return result;
    }
}
