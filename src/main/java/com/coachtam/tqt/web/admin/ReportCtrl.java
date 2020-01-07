package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.to.FeedbackForm;
import com.coachtam.tqt.vo.admin.EchartLineStackVO;
import com.coachtam.tqt.vo.admin.EchartVO;
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


    /**
     * 整体学习曲线
     * @param searchForm
     * @return
     */
    @PostMapping("/learncurve")
    public EchartVO learncurve(@RequestBody FeedbackForm searchForm)
    {

        EchartVO result = feedbackService.learnCurve(searchForm);

        return result;
    }
    /**
     * 个人学习曲线
     * @param searchForm
     * @return
     */
    @PostMapping("/learncurvepro")
    public EchartLineStackVO learncurvepro(@RequestBody FeedbackForm searchForm)
    {

        EchartLineStackVO result = feedbackService.learncurvepro(searchForm);

        return result;
    }
}
