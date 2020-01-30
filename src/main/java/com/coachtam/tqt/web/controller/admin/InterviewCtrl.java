package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.config.properties.UploadProperteis;
import com.coachtam.tqt.entity.Interview;
import com.coachtam.tqt.service.InterviewService;
import com.coachtam.tqt.qo.InterviewQO;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:58:27
 */
@RequestMapping("/api/interview")
@RestController
@EnableConfigurationProperties(UploadProperteis.class)
@Api(value = "面试服务")
public class InterviewCtrl {

    @Autowired
    private UploadProperteis uploadProperteis;
    @Autowired
    private InterviewService interviewService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/search/{all}")
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, @RequestBody InterviewQO searchForm, @PathVariable("all")Boolean all)
    {
        Page<Interview> result = interviewService.page(pageNo,pageSize,searchForm,all);
        result.forEach(interview ->{
                //拼接完整访问地址
                if(StringUtils.isNotBlank(interview.getSoundRecording()))
                {
                    interview.setSoundRecording(uploadProperteis.getAccessPath()+"/sound/"+interview.getSoundRecording());
                }


                if(StringUtils.isNotBlank(interview.getAppendixs()))
                {
                    List<String> imgs = Arrays.stream(interview.getAppendixs().split(",")).map(img ->
                             StringUtils.isNotBlank(img)?uploadProperteis.getAccessPath() + "/image/" + img:img
                    ).collect(Collectors.toList());
                    interview.setAppendixs(StringUtils.join(imgs, ","));
                }

            }
        );
        return ResultVM.success(result);
    }



    @ApiOperation(value = "根据id查询面试详情")
    @GetMapping("/{id}")
    public ResultVM<Interview> detail(@PathVariable("id") Integer id)
    {
        Interview interview = interviewService.findById(id);

        return ResultVM.success(interview);
    }


    @GetMapping("/all")
    public ResultVM<List<Interview>> getAll()
    {
        List<Interview> result = interviewService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        interviewService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody Interview interview)
    {
        interviewService.update(interview);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody Interview interview)
    {
        interviewService.save(interview);
        return ResultVM.success(null);
    }
}
