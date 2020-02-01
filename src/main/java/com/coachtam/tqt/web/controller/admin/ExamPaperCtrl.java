package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.qo.ExamPaperQO;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionResponseVM;
import com.coachtam.tqt.web.converter.ExamPaperConverter;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:	试卷
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:28:32
 */
@RequestMapping("/api/examPaper")
@RestController
public class ExamPaperCtrl {

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private ExamPaperConverter examPaperConverter;
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PostMapping("page")
    public ResultVM<Map<String, Object>> list(Integer pageNo, Integer pageSize, @RequestBody ExamPaperQO searchForm)
    {
        Specification<ExamPaper> specification = (root, query, builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(searchForm.getClassId()!=null && !searchForm.getClassId().isEmpty() &&!"all".equals(searchForm.getClassId()))
            {
                Join<ExamPaper, Classes> joins = root.join("classes");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getClassId());

                predicates.add(equal);
            }
            if(searchForm.getName()!=null && !searchForm.getName().isEmpty())
            {
                Predicate equal = builder.like(root.get("name"), "%"+searchForm.getName()+"%");
                predicates.add(equal);
            }
            if(searchForm.getCourseId()!=null && !searchForm.getCourseId().isEmpty())
            {
                Predicate equal = builder.equal(root.join("course").get("id"), searchForm.getCourseId());
                predicates.add(equal);
            }
//            //查询可用的
//            Predicate equal = builder.equal(root.get("deleted"), 0);
//            predicates.add(equal);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page page = examPaperService.page(pageNo,pageSize,specification);

        List<QuestionResponseVM> vmList = examPaperConverter.domain2dto(page.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("content", vmList);
        map.put("number", page.getNumber());
        map.put("size", page.getSize());
        return ResultVM.success(map);
    }


    @GetMapping("/{id}")
    public ResultVM<ExamPaper> list(@PathVariable("id") Integer id)
    {
        ExamPaper examPaper = examPaperService.findById(id);

        return ResultVM.success(examPaper);
    }

    @RolesAllowed({"老师","管理员","测试","班主任"})
    @GetMapping("/all")
    public ResultVM<List<ExamPaper>> getAll()
    {
        List<ExamPaper> result = examPaperService.findAll();
        return ResultVM.success(result);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperService.deleteByIds(ids);
        return ResultVM.success(null);
    }

    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PutMapping
    public ResultVM<Integer> update(@RequestBody @Valid ExamPaperEditRequestVM model)
    {
        examPaperService.update(model);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PostMapping
    public ResultVM<Integer> add(@RequestBody @Valid ExamPaperEditRequestVM model)
    {
        examPaperService.save(model);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public ResultVM<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return ResultVM.success(vm);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PutMapping("status")
    public ResultVM<ExamPaperEditRequestVM> status(@RequestParam("deleted")Boolean deleted,@RequestParam("paperId")Integer paperId){
        examPaperService.updateStatus(paperId,deleted);
        return ResultVM.success(null);
    }
}
