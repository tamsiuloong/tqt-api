package com.coachtam.tqt.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExamUtil {

    public static String scoreToVM(Integer score) {
            return String.valueOf(score);
    }

    public static Integer scoreToIntegerVM(Integer score) {
          return score ;
    }

    public static Integer scoreFromVM(String score) {
        if (StringUtils.isBlank(score)) {
            return null;
        } else {
            return (int) (Float.parseFloat(score));
        }
    }

    public static String secondToVM(Integer second) {
        String dateTimes;
        long days = second / (60 * 60 * 24);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        long minutes = (second % (60 * 60)) / 60;
        long seconds = second % 60;
        if (days > 0) {
            dateTimes = days + "天 " + hours + "时 " + minutes + "分 " + seconds + "秒";
        } else if (hours > 0) {
            dateTimes = hours + "时 " + minutes + "分 " + seconds + "秒";
        } else if (minutes > 0) {
            dateTimes = minutes + "分 " + seconds + "秒";
        } else {
            dateTimes = seconds + " 秒";
        }
        return dateTimes;
    }

    private static final String ANSWER_SPLIT = ",";

    public static String contentToString(List<String> contentArray) {
        return contentArray.stream().sorted().collect(Collectors.joining(ANSWER_SPLIT));
    }


    public static List<String> contentToArray(String contentArray) {
        if(contentArray==null)
        {
            return Collections.emptyList();
        }
        return Arrays.asList(contentArray.split(ANSWER_SPLIT));
    }

    private static final String FORM_ANSWER_SPLIT = "_";

    public static Integer lastNum(String str) {
        Integer start = str.lastIndexOf(FORM_ANSWER_SPLIT);
        return Integer.parseInt(str.substring(start + 1));
    }
}
