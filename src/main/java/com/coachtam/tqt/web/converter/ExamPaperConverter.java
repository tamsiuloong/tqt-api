package com.coachtam.tqt.web.converter;

import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.viewmodel.admin.exam.ExamResponseVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-30 17:52
 * @Description:
 */
@Mapper(componentModel="spring")
public interface ExamPaperConverter {


    @Mappings({
            @Mapping(target = "createTime", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(examPaper.getCreateTime(),\"yyyy-MM-dd HH:mm:ss\"))"),
            @Mapping(source = "course.name",target = "courseName"),
            @Mapping(target = "classesName", expression = "java(examPaper.getClasses().getName()+\"-\"+examPaper.getClasses().getType())"),
            @Mapping(source = "user.userInfo.name",target = "createUser")
    })
    ExamResponseVM domain2dto(ExamPaper examPaper);

    List<ExamResponseVM> domain2dto(List<ExamPaper> examPapers);

}
