package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.to.ExamPaperForm;
import com.coachtam.tqt.vo.admin.ResultVO;
import com.coachtam.tqt.vo.admin.exam.ExamPaperEditRequestVM;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize,@RequestBody ExamPaperForm searchForm)
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
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<ExamPaper> list(@PathVariable("id") Integer id)
    {
        ExamPaper examPaper = examPaperService.findById(id);

        return ResultVO.success(examPaper);
    }


    @GetMapping("/all")
    public ResultVO<List<ExamPaper>> getAll()
    {
        List<ExamPaper> result = examPaperService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody @Valid ExamPaperEditRequestVM model)
    {
        examPaperService.update(model);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody @Valid ExamPaperEditRequestVM model)
    {
        examPaperService.save(model);
        return ResultVO.success(null);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public ResultVO<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return ResultVO.success(vm);
    }
}
