package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.VoteSubtopic;
import com.coachtam.tqt.service.VoteSubtopicService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	投票子题目
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:01
 */
@RequestMapping("/api/voteSubtopic")
@RestController
public class VoteSubtopicCtrl {

    @Autowired
    private VoteSubtopicService voteSubtopicService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteSubtopicService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<VoteSubtopic> list(@PathVariable("id") String id)
    {
        VoteSubtopic voteSubtopic = voteSubtopicService.findById(id);

        return ResultVM.success(voteSubtopic);
    }


    @GetMapping("/all/{votetopicId}")
    public ResultVM<List<VoteSubtopic>> getAll(@PathVariable("votetopicId")Integer votetopicId)
    {
        List<VoteSubtopic> result = voteSubtopicService.findAllByVotetopicId(votetopicId);
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        voteSubtopicService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody VoteSubtopic voteSubtopic)
    {
        voteSubtopicService.update(voteSubtopic);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody VoteSubtopic voteSubtopic)
    {
        voteSubtopicService.save(voteSubtopic);
        return ResultVM.success(null);
    }
}
