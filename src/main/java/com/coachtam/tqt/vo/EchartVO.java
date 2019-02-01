package com.coachtam.tqt.vo;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-31 12:39
 * @Description:
 */
@Data
public class EchartVO {
    private List<String> titles = new ArrayList<>();
    private List<BigInteger> values = new ArrayList<>();
}
