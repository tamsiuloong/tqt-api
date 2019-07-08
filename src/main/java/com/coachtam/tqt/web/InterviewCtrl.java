package com.coachtam.tqt.web;

import com.coachtam.tqt.config.properties.UploadProperteis;
import com.coachtam.tqt.entity.Interview;
import com.coachtam.tqt.service.InterviewService;
import com.coachtam.tqt.to.FeedbackForm;
import com.coachtam.tqt.to.InterviewForm;
import com.coachtam.tqt.vo.ResultVO;
import org.apache.commons.lang.ArrayUtils;
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
public class InterviewCtrl {

    @Autowired
    private UploadProperteis uploadProperteis;
    @Autowired
    private InterviewService interviewService;

    @PostMapping("/search/{all}")
    public ResultVO<Page> list(Integer pageNo, Integer pageSize,@RequestBody InterviewForm searchForm,@PathVariable("all")Boolean all)
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
        return ResultVO.success(result);
    }




    @GetMapping("/{id}")
    public ResultVO<Interview> list(@PathVariable("id") Integer id)
    {
        Interview interview = interviewService.findById(id);

        return ResultVO.success(interview);
    }


    @GetMapping("/all")
    public ResultVO<List<Interview>> getAll()
    {
        List<Interview> result = interviewService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        interviewService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody Interview interview)
    {
        interviewService.update(interview);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody Interview interview)
    {
        interviewService.save(interview);
        return ResultVO.success(null);
    }
}
