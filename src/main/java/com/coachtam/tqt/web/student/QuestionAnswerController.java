package com.coachtam.tqt.web.student;

import com.coachtam.tqt.config.utils.JsonUtil;
import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.entity.question.QuestionObject;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.service.*;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.HtmlUtil;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.ModelMapperSingle;
import com.coachtam.tqt.vo.admin.question.QuestionEditRequestVM;
import com.coachtam.tqt.vo.student.answer.QuestionAnswerVM;
import com.coachtam.tqt.vo.student.answer.QuestionPageStudentRequestVM;
import com.coachtam.tqt.vo.student.answer.QuestionPageStudentResponseVM;
import com.coachtam.tqt.vo.student.base.RestResponse;
import com.coachtam.tqt.vo.student.exam.ExamPaperSubmitItemVM;
import com.coachtam.tqt.vo.student.exampaper.ExamPaperAnswerPageResponseVM;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("StudentQuestionAnswerController")
@RequestMapping(value = "/api/student/question/answer")
@AllArgsConstructor
public class QuestionAnswerController{

    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final QuestionService questionService;
    private final TextContentService textContentService;
    private final CourseService courseService;
    private final UserService userService;
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> pageList(@RequestBody QuestionPageStudentRequestVM model) {

        User user = userService.findByUsername(LoginInterceptor.getCurrUser().getUsername());
        model.setCreateUser(user.getId());

        Page<ExamPaperQuestionCustomerAnswer> page = examPaperQuestionCustomerAnswerService.findByUserId(model);

        List<QuestionPageStudentResponseVM> collect = page.getContent().stream().map(q->{
            Course course = courseService.findById(q.getCourseId());
            QuestionPageStudentResponseVM vm = modelMapper.map(q, QuestionPageStudentResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            if(q.getQuestionTextContentId()!=null)
            {
                Question question = questionService.findById(q.getQuestionId());
//                QuestionObject questionObject = JsonUtils.toJsonObject(question.getTitle(), QuestionObject.class);
                String clearHtml = HtmlUtil.clear(question.getTitle());
                vm.setShortTitle(clearHtml);
            }

            vm.setCourseName(course.getName());
            return vm;
        }).collect(Collectors.toList());


        Map<String, Object> map = new HashMap<>();
        map.put("list", collect);
        map.put("total", page.getTotalElements());
        map.put("pageNum", page.getNumber()+1);
        return RestResponse.ok(map);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionAnswerVM> select(@PathVariable Integer id) {
        QuestionAnswerVM vm = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.findById(id);
        ExamPaperSubmitItemVM questionAnswerVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }

}
