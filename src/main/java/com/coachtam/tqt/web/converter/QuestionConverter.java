package com.coachtam.tqt.web.converter;

import com.coachtam.tqt.entity.Question;
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
public interface QuestionConverter {

//    QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(question.getCreateTime(),\"yyyy-MM-dd HH:mm:ss\"))"),
            @Mapping(target = "shortTitle", expression = "java(com.coachtam.tqt.utils.HtmlUtil.clear(question.getTitle()))"),
            @Mapping(source = "course.name",target = "courseName"),
            @Mapping(source = "user.userInfo.name",target = "createUser")
    })
    QuestionResponseVM domain2dto(Question question);

    List<QuestionResponseVM> domain2dto(List<Question> question);

}
