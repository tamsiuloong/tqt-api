package com.coachtam.tqt.web.student;


import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.enums.ExamPaperTypeEnum;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.service.QuestionService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.viewmodel.student.base.RestResponse;
import com.coachtam.tqt.viewmodel.student.dashboard.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("StudentDashboardController")
@RequestMapping(value = "/api/student/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final ExamPaperService examPaperService;
    private final QuestionService questionService;
//    private final TaskExamService taskExamService;
//    private final TaskExamCustomerAnswerService taskExamCustomerAnswerService;
//    private final TextContentService textContentService;

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> index() {
        IndexVM indexVM = new IndexVM();
//        User user = LoginInterceptor.getCurrUser();
        List<ExamPaper> list = examPaperService.findPaperByType(ExamPaperTypeEnum.Fixed.getCode());

        if(list!=null)
        {
            indexVM.setFixedPaper(list.stream().map(ep->{
                PaperInfo paperInfo = new PaperInfo();
                paperInfo.setId(ep.getId());
                paperInfo.setName(ep.getName());
                return paperInfo;
            }).collect(Collectors.toList()));

            List<ExamPaper> limitPaper = examPaperService.findPaperByType(ExamPaperTypeEnum.TimeLimit.getCode());

            List<PaperInfoVM> paperInfoVMS = limitPaper.stream().map(ep -> {
                PaperInfoVM paperInfo = new PaperInfoVM();
                paperInfo.setId(ep.getId());
                paperInfo.setName(ep.getName());
                paperInfo.setStartTime(DateTimeUtil.dateFormat(ep.getLimitStartTime()));
                paperInfo.setEndTime(DateTimeUtil.dateFormat(ep.getLimitEndTime()));
                return paperInfo;
            }).collect(Collectors.toList());

            indexVM.setTimeLimitPaper(paperInfoVMS);
        }


        return RestResponse.ok(indexVM);
    }
//
//
//    @RequestMapping(value = "/task", method = RequestMethod.POST)
//    public RestResponse<List<TaskItemVm>> task() {
//        User user = getCurrentUser();
//        List<TaskExam> taskExams = taskExamService.getByGradeLevel(user.getUserLevel());
//        if (taskExams.size() == 0) {
//            return RestResponse.ok(new ArrayList<>());
//        }
//        List<Integer> tIds = taskExams.stream().map(taskExam -> taskExam.getId()).collect(Collectors.toList());
//        List<TaskExamCustomerAnswer> taskExamCustomerAnswers = taskExamCustomerAnswerService.selectByTUid(tIds, user.getId());
//        List<TaskItemVm> vm = taskExams.stream().map(t -> {
//            TaskItemVm itemVm = new TaskItemVm();
//            itemVm.setId(t.getId());
//            itemVm.setTitle(t.getTitle());
//            TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswers.stream()
//                    .filter(tc -> tc.getTaskExamId().equals(t.getId())).findFirst().orElse(null);
//            List<TaskItemPaperVm> paperItemVMS = getTaskItemPaperVm(t.getFrameTextContentId(), taskExamCustomerAnswer);
//            itemVm.setPaperItems(paperItemVMS);
//            return itemVm;
//        }).collect(Collectors.toList());
//        return RestResponse.ok(vm);
//    }
//
//
//    private List<TaskItemPaperVm> getTaskItemPaperVm(Integer tFrameId, TaskExamCustomerAnswer taskExamCustomerAnswers) {
//        TextContent textContent = textContentService.selectById(tFrameId);
//        List<TaskItemObject> paperItems = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class);
//
//        List<TaskItemAnswerObject> answerPaperItems = null;
//        if (null != taskExamCustomerAnswers) {
//            TextContent answerTextContent = textContentService.selectById(taskExamCustomerAnswers.getTextContentId());
//            answerPaperItems = JsonUtil.toJsonListObject(answerTextContent.getContent(), TaskItemAnswerObject.class);
//        }
//
//
//        List<TaskItemAnswerObject> finalAnswerPaperItems = answerPaperItems;
//        return paperItems.stream().map(p -> {
//                    TaskItemPaperVm ivm = new TaskItemPaperVm();
//                    ivm.setExamPaperId(p.getExamPaperId());
//                    ivm.setExamPaperName(p.getExamPaperName());
//                    if (null != finalAnswerPaperItems) {
//                        finalAnswerPaperItems.stream()
//                                .filter(a -> a.getExamPaperId().equals(p.getExamPaperId()))
//                                .findFirst()
//                                .ifPresent(a -> {
//                                    ivm.setExamPaperAnswerId(a.getExamPaperAnswerId());
//                                    ivm.setStatus(a.getStatus());
//                                });
//                    }
//                    return ivm;
//                }
//        ).collect(Collectors.toList());
//    }
}
