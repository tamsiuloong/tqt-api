package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.to.ExamPaperForm;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperEditRequestVM;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("page")
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, @RequestBody ExamPaperForm searchForm)
    {
        Specification<ExamPaper> specification = (root, query, builder)->{
            List<Predicate> predicates = Lists.newArrayList();

//            query.multiselect(builder.count(root.get("absorption")))
//                 .multiselect(root.get("absorption"));
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
            //查询可用的
            Predicate equal = builder.equal(root.get("deleted"), 0);
            predicates.add(equal);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page result = examPaperService.page(pageNo,pageSize,specification);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<ExamPaper> list(@PathVariable("id") Integer id)
    {
        ExamPaper examPaper = examPaperService.findById(id);

        return ResultVM.success(examPaper);
    }


    @GetMapping("/all")
    public ResultVM<List<ExamPaper>> getAll()
    {
        List<ExamPaper> result = examPaperService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody @Valid ExamPaperEditRequestVM model)
    {
        examPaperService.update(model);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody @Valid ExamPaperEditRequestVM model)
    {
        examPaperService.save(model);
        return ResultVM.success(null);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public ResultVM<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return ResultVM.success(vm);
    }
}
