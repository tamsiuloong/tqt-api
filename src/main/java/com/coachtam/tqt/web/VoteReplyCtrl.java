package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.VoteReply;
import com.coachtam.tqt.service.VoteReplyService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	投票问题题目回复
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:29
 */
@RequestMapping("/api/voteReply")
@RestController
public class VoteReplyCtrl {

    @Autowired
    private VoteReplyService voteReplyService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteReplyService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<VoteReply> list(@PathVariable("id") String id)
    {
        VoteReply voteReply = voteReplyService.findById(id);

        return ResultVO.success(voteReply);
    }


    @GetMapping("/all")
    public ResultVO<List<VoteReply>> getAll()
    {
        List<VoteReply> result = voteReplyService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        voteReplyService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody VoteReply voteReply)
    {
        voteReplyService.update(voteReply);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody VoteReply voteReply)
    {
        voteReplyService.save(voteReply);
        return ResultVO.success(null);
    }
}
