package com.coachtam.tqt.viewmodel.student.user;

import lombok.Data;

@Data
public class UserEventLogVM {

    private Integer id;

    private String userId;

    private String userName;

    private String realName;

    private String content;

    private String createTime;

}
