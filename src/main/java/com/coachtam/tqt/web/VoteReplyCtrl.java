package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.entity.VoteReply;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.service.VoteRecordService;
import com.coachtam.tqt.service.VoteReplyService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    @Autowired
    private UserService userService;

    @Autowired
    private VoteRecordService voteRecordService;
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
    public ResultVO<String> add(@RequestBody List<VoteReply> voteReplyList, @RequestParam("voteTopicId")Integer voteTopicId, HttpServletRequest request)
    {
        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        User dbUser = userService.findByUsername(username);

        //判断是否已经提交过
        Boolean hasCommited = voteRecordService.findByVoteTopicId(voteTopicId,dbUser.getId());

        if(hasCommited)
        {
           return ResultVO.fail("已经提交过该问卷调查!");
        }

        VoteRecord voteRecord = new VoteRecord();
        voteRecord.setVotetopicId(voteTopicId);
        voteRecord.setUser(dbUser);
        voteRecord.setVoteIp(request.getRemoteAddr());
        voteRecord.setVoteTime(new Date());

        voteReplyService.save(voteReplyList,voteRecord);
        return ResultVO.success(null);
    }
}
