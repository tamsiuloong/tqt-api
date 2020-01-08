package com.coachtam.tqt.viewmodel.admin.exam;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.utils.StringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ExamPaperEditRequestVM {
    private Integer id;

    private Integer level;

    private Integer subjectId;
    @NotNull
    @JsonSerialize(using = StringSerializer.class)
    private Integer paperType;
    @NotBlank
    private String name;
    @NotNull
    private Integer suggestTime;

    private List<String> limitDateTime;

    @Size(min = 1,message = "请添加试卷标题")
    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

    private String score;

    private Classes classes;

    private Course course;

}
