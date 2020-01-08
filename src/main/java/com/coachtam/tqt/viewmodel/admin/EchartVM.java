package com.coachtam.tqt.viewmodel.admin;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-31 12:39
 * @Description:
 */
@Data
public class EchartVM {
    private List<String> titles = new ArrayList<>();
    private List<Long> values = new ArrayList<>();
}
