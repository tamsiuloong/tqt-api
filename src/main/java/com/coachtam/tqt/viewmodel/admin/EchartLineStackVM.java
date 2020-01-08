package com.coachtam.tqt.viewmodel.admin;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 折线图堆叠VO
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-31 12:39
 * @Description:
 */
@Data
public class EchartLineStackVM {
    private List<String> legendData = new ArrayList<>();
    private List<String> xData = new ArrayList<>();
    private List<Map<String,Object>> series = new ArrayList<>();
    //默认选中的名字
    private Map<String,Object> selected = new HashMap<>();


}

