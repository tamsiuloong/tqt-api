package com.coachtam.tqt.viewmodel.admin;

import com.coachtam.tqt.entity.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-05-13 17:46
 * @Description:
 */
@Data
public class FeedbackVM {
   //分页对象
   private  Page page;
   //未提交名单
   private List<User> unCommitedList;
}
