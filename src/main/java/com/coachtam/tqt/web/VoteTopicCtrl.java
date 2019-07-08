package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.VoteTopic;
import com.coachtam.tqt.service.VoteTopicService;
import com.coachtam.tqt.vo.ResultVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteTopicService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<VoteTopic> list(@PathVariable("id") String id)
    {
        VoteTopic voteTopic = voteTopicService.findById(id);

        return ResultVO.success(voteTopic);
    }


    @GetMapping("/all")
    public ResultVO<List<VoteTopic>> getAll()
    {
        List<VoteTopic> result = voteTopicService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        voteTopicService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody VoteTopic voteTopic)
    {
        voteTopicService.update(voteTopic);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody VoteTopic voteTopic)
    {
        voteTopicService.save(voteTopic);
        return ResultVO.success(null);
    }
}
