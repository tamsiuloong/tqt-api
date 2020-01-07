package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.VoteSubtopic;
import com.coachtam.tqt.service.VoteSubtopicService;
import com.coachtam.tqt.vo.admin.ResultVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteSubtopicService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<VoteSubtopic> list(@PathVariable("id") String id)
    {
        VoteSubtopic voteSubtopic = voteSubtopicService.findById(id);

        return ResultVO.success(voteSubtopic);
    }


    @GetMapping("/all/{votetopicId}")
    public ResultVO<List<VoteSubtopic>> getAll(@PathVariable("votetopicId")Integer votetopicId)
    {
        List<VoteSubtopic> result = voteSubtopicService.findAllByVotetopicId(votetopicId);
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        voteSubtopicService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody VoteSubtopic voteSubtopic)
    {
        voteSubtopicService.update(voteSubtopic);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody VoteSubtopic voteSubtopic)
    {
        voteSubtopicService.save(voteSubtopic);
        return ResultVO.success(null);
    }
}
