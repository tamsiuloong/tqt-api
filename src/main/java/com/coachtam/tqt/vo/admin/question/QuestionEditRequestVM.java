package com.coachtam.tqt.vo.admin.question;

import com.coachtam.tqt.entity.Course;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuestionEditRequestVM {

    private Integer id;
    @NotNull
    private Integer questionType;
    @NotNull
    private Course course;
    @NotBlank
    private String title;

    private Integer gradeLevel;

    @Valid
    private List<QuestionEditItemVM> items;
    @NotBlank
    private String analyze;

    private List<String> correctArray;

    private String correct;
    @NotNull
    private Integer score;

    @Range(min = 1, max = 5, message = "请选择题目难度")
    private Integer difficult;

    private Integer itemOrder;
}
