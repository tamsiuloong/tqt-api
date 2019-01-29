package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-28 16:58
 * @Description:
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackCtrl {
    @Autowired
    private FeedbackService feedbackService;
    @PostMapping
    public String save(@RequestBody Feedback feedback)
    {
        feedbackService.save(feedback);
        return "1";
    }

    @GetMapping
    public String get()
    {
        return "1";
    }
}
