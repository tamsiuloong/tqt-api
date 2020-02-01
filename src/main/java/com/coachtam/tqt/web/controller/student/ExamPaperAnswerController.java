package com.coachtam.tqt.web.controller.student;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.entity.enums.ExamPaperAnswerStatusEnum;
import com.coachtam.tqt.event.CalculateExamPaperAnswerCompleteEvent;
import com.coachtam.tqt.event.UserEvent;
import com.coachtam.tqt.interceptor.AuthInterceptor;
import com.coachtam.tqt.service.CourseService;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.ModelMapperSingle;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.coachtam.tqt.viewmodel.student.base.RestResponse;
import com.coachtam.tqt.viewmodel.student.exam.ExamPaperReadVM;
import com.coachtam.tqt.viewmodel.student.exam.ExamPaperSubmitVM;
import com.coachtam.tqt.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.coachtam.tqt.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("StudentExamPaperAnswerController")
@RequestMapping(value = "/api/student/exampaper/answer")
@AllArgsConstructor
public class ExamPaperAnswerController  {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final ExamPaperService examPaperService;
//    private final SubjectService subjectService;
    private final ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();



    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> pageList(@RequestBody @Valid ExamPaperAnswerPageVM model) {
        User user = userService.findByUsername(AuthInterceptor.getCurrUser().getUsername());
        model.setCreateUser(user.getId());

        Page<ExamPaperAnswer> page = examPaperAnswerService.findByUserIdAndCourseId(model);

        List<ExamPaperAnswerPageResponseVM> collect = page.getContent().stream().map(e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Course course = courseService.findById(e.getCourseId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setCourseName(course.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("list", collect);
        map.put("total", page.getTotalElements());
        map.put("pageNum", page.getNumber()+1);

        return RestResponse.ok(map);
    }


    @RequestMapping(value = "/answerSubmit", method = RequestMethod.POST)
    public RestResponse<String> answerSubmit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM) {
        User user = userService.findByUsername(AuthInterceptor.getCurrUser().getUsername()) ;

        ExamPaperAnswerInfo examPaperAnswerInfo = examPaperAnswerService.calculateExamPaperAnswer(examPaperSubmitVM, user);
        if (null == examPaperAnswerInfo) {
            return RestResponse.fail(2, "试卷不能重复做");
        }
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        Integer userScore = examPaperAnswer.getUserScore();
        String scoreVm = ExamUtil.scoreToVM(userScore);
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getUserInfo().getName(), new Date());
        String content = user.getUserName() + " 提交试卷：" + examPaperAnswerInfo.getExamPaper().getName()
                + " 得分：" + scoreVm
                + " 耗时：" + ExamUtil.secondToVM(examPaperAnswer.getDoTime());
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new CalculateExamPaperAnswerCompleteEvent(examPaperAnswerInfo));
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(scoreVm);
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<String> edit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM) {
        boolean notJudge = examPaperSubmitVM.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
        if (notJudge) {
            return RestResponse.fail(2, "有未批改题目");
        }

        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.findById(examPaperSubmitVM.getId());
        ExamPaperAnswerStatusEnum examPaperAnswerStatusEnum = ExamPaperAnswerStatusEnum.fromCode(examPaperAnswer.getStatus());
        if (examPaperAnswerStatusEnum == ExamPaperAnswerStatusEnum.Complete) {
            return RestResponse.fail(3, "试卷已完成");
        }
        String score = examPaperAnswerService.judge(examPaperSubmitVM);
        User user = userService.findByUsername(AuthInterceptor.getCurrUser().getUsername());
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getUserInfo().getName(), new Date());
        String content = user.getUserName() + " 批改试卷：" + examPaperAnswer.getPaperName() + " 得分：" + score;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(score);
    }
    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperReadVM> read(@PathVariable Integer id) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.findById(id);
        ExamPaperReadVM vm = new ExamPaperReadVM();
        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }


}
