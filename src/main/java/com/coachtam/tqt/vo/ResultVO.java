package com.coachtam.tqt.vo;

import javafx.scene.control.Pagination;
import org.springframework.data.domain.Page;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-30 13:41
 * @Description:
 */
public class ResultVO<T> {

    private Integer code;
    private String desc;
    private T data;

    public static <T> ResultVO<T> success(T data) {
      ResultVO<T> result =   new ResultVO<>();
      result.setCode(1);
      result.setDesc("success");
      result.setData(data);
      return result;
    }


    public static <T> ResultVO<T> fail(T data) {
        ResultVO<T> result =   new ResultVO<>();
        result.setCode(0);
        result.setDesc("fail");
        result.setData(data);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
