package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.entity.VoteReply;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.service.VoteRecordService;
import com.coachtam.tqt.service.VoteReplyService;
import com.coachtam.tqt.utils.jwt.UserInfo;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteReplyService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<VoteReply> list(@PathVariable("id") String id)
    {
        VoteReply voteReply = voteReplyService.findById(id);

        return ResultVM.success(voteReply);
    }


    @GetMapping("/all")
    public ResultVM<List<VoteReply>> getAll()
    {
        List<VoteReply> result = voteReplyService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        voteReplyService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody VoteReply voteReply)
    {
        voteReplyService.update(voteReply);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody List<VoteReply> voteReplyList, @RequestParam("voteTopicId")Integer voteTopicId, HttpServletRequest request)
    {
        UserInfo user = LoginInterceptor.getCurrUser();
        User dbuser = userService.findByUsername(user.getUsername());

        //判断是否已经提交过
        Boolean hasCommited = voteRecordService.findByVoteTopicId(voteTopicId,dbuser.getId());

        if(hasCommited)
        {
           return ResultVM.fail("已经提交过该问卷调查!");
        }

        VoteRecord voteRecord = new VoteRecord();
        voteRecord.setVotetopicId(voteTopicId);
        voteRecord.setUser(dbuser);
        voteRecord.setVoteIp(request.getRemoteAddr());
        voteRecord.setVoteTime(new Date());

        voteReplyService.save(voteReplyList,voteRecord);
        return ResultVM.success(null);
    }
}
