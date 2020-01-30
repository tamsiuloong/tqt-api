package com.coachtam.tqt.web.controller.student;



import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.service.CourseService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.ModelMapperSingle;
import com.coachtam.tqt.viewmodel.student.base.RestResponse;
import com.coachtam.tqt.viewmodel.student.education.CourseEditRequestVM;
import com.coachtam.tqt.viewmodel.student.education.CourseVM;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("StudentEducationController")
@RequestMapping(value = "/api/student/education")
@AllArgsConstructor
public class EducationController  {

    private final CourseService courseService;
    private UserService userService;
    protected static ModelMapper modelMapper = ModelMapperSingle.Instance();

    @RequestMapping(value = "/subject/list", method = RequestMethod.POST)
    public RestResponse<List<CourseVM>> list() {
//        User user = userService.findByUsername(LoginInterceptor.getCurrUser().getUsername());

        List<Course> subjects = courseService.findAll();
        List<CourseVM> courseVMS = subjects.stream().map(d -> {
            CourseVM courseVM = modelMapper.map(d, CourseVM.class);
            courseVM.setId(String.valueOf(d.getId()));
            return courseVM;
        }).collect(Collectors.toList());
        return RestResponse.ok(courseVMS);
    }

    @RequestMapping(value = "/subject/select/{id}", method = RequestMethod.POST)
    public RestResponse<CourseEditRequestVM> select(@PathVariable String id) {
        Course subject = courseService.findById(id);
        CourseEditRequestVM vm = modelMapper.map(subject, CourseEditRequestVM.class);
        return RestResponse.ok(vm);
    }

}
