package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.VoteTopic;
import com.coachtam.tqt.service.VoteTopicService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	投票主题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:50:38
 */
@RequestMapping("/api/voteTopic")
@RestController
public class VoteTopicCtrl {

    @Autowired
    private VoteTopicService voteTopicService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteTopicService.page(pageNo,pageSize,true);
        return ResultVM.success(result);
    }

    @GetMapping("/mine")
    public ResultVM<Page> mylist(Integer pageNo, Integer pageSize)
    {
        Page result = voteTopicService.page(pageNo,pageSize,false);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<VoteTopic> list(@PathVariable("id") Integer id)
    {
        VoteTopic voteTopic = voteTopicService.findById(id);

        return ResultVM.success(voteTopic);
    }


    @GetMapping("/all")
    public ResultVM<List<VoteTopic>> getAll()
    {
        List<VoteTopic> result = voteTopicService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody Integer[] ids)
    {
        voteTopicService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody VoteTopic voteTopic)
    {
        voteTopicService.update(voteTopic);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody VoteTopic voteTopic)
    {
        voteTopicService.save(voteTopic);
        return ResultVM.success(null);
    }
}
