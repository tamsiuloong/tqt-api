package com.coachtam.tqt.vo.student.dashboard;

import lombok.Data;

import java.util.Date;

@Data
public class PaperInfo {
    private Integer id;
    private String name;
    private Date limitStartTime;
    private Date limitEndTime;
}
