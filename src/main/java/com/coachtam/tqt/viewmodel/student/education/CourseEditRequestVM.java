package com.coachtam.tqt.viewmodel.student.education;

import com.coachtam.tqt.utils.ModelMapperSingle;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CourseEditRequestVM {

    protected static ModelMapper modelMapper = ModelMapperSingle.Instance();

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer level;

    @NotBlank
    private String levelName;

}
