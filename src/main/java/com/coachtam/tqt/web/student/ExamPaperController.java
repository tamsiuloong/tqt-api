package com.coachtam.tqt.web.student;


import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.vo.admin.exam.ExamPaperEditRequestVM;
import com.coachtam.tqt.vo.student.base.RestResponse;
import com.coachtam.tqt.vo.student.exam.ExamPaperPageResponseVM;
import com.coachtam.tqt.vo.student.exam.ExamPaperPageVM;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
@AllArgsConstructor
public class ExamPaperController  {

    private final ExamPaperService examPaperService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


//    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
//    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@RequestBody @Valid ExamPaperPageVM model) {
//        PageInfo<ExamPaper> pageInfo = examPaperService.studentPage(model);
//        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
//            ExamPaperPageResponseVM vm = modelMapper.map(e, ExamPaperPageResponseVM.class);
//            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
//            return vm;
//        });
//        return RestResponse.ok(page);
//    }
}
