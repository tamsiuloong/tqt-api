package com.coachtam.tqt.web.student;


import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.ModelMapperSingle;
import com.coachtam.tqt.vo.admin.exam.ExamPaperEditRequestVM;
import com.coachtam.tqt.vo.student.base.RestResponse;
import com.coachtam.tqt.vo.student.exam.ExamPaperPageResponseVM;
import com.coachtam.tqt.vo.student.exam.ExamPaperPageVM;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
@AllArgsConstructor
public class ExamPaperController  {

    private final ExamPaperService examPaperService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> pageList(@RequestBody @Valid ExamPaperPageVM model) {


        Specification<ExamPaper> specification = (root, query, builder)->{
            List<Predicate> predicates = Lists.newArrayList();
            if(model.getCourseId()!=null && !model.getCourseId().isEmpty())
            {
                Predicate equal = builder.equal(root.join("course").get("id"), model.getCourseId());
                predicates.add(equal);
            }

            if(model.getPaperType()!=null)
            {
                Predicate equal = builder.equal(root.get("paperType"), model.getPaperType());
                predicates.add(equal);
            }
            //查询可用的
            Predicate equal = builder.equal(root.get("deleted"), 0);
            predicates.add(equal);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Page<ExamPaper> page = examPaperService.page(model.getPageIndex(), model.getPageSize(), specification);

        List<ExamPaperPageResponseVM> collect = page.getContent().stream().map(ep->{
            ExamPaperPageResponseVM vm = modelMapper.map(ep, ExamPaperPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(ep.getCreateTime()));
            return vm;
        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("list", collect);
        map.put("total", page.getTotalElements());
        map.put("pageNum", page.getNumber()+1);
        return RestResponse.ok(map);
    }
}
