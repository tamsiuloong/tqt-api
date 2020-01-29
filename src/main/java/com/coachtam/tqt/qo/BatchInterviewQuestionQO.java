package com.coachtam.tqt.qo;

import com.coachtam.tqt.entity.InterviewQuestion;
import lombok.Data;

import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-02-03 16:41
 * @Description:
 */
@Data
public class BatchInterviewQuestionQO {
    private String company;
    private String text;
    private List<InterviewQuestion> interviewQuestionList;
}
