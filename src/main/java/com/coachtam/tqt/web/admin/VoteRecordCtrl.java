package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.config.security.UserDetail;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.service.VoteRecordService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:	投票记录
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:46
 */
@RequestMapping("/api/voteRecord")
@RestController
public class VoteRecordCtrl {

    @Autowired
    private VoteRecordService voteRecordService;


    @Autowired
    private UserService userService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteRecordService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<VoteRecord> list(@PathVariable("id") String id)
    {
        VoteRecord voteRecord = voteRecordService.findById(id);

        return ResultVM.success(voteRecord);
    }


    @GetMapping("/all/{votetopicId}")
    public ResultVM<List<VoteRecord>> getAll(@PathVariable("votetopicId")Integer votetopicId)
    {
        List<VoteRecord> result = voteRecordService.findAll(votetopicId);
         UserDetail user  = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbuser = userService.findByUsername(user.getUsername());
        if(dbuser.getRoleSet().stream().anyMatch(role -> "老师".equals(role.getName()))&&!dbuser.getRoleSet().stream().anyMatch(role -> "管理员".equals(role.getName()))) {
            result = result.stream().map(vr -> {
                        vr.getUser().getUserInfo().setName("***");
                        return vr;
                    }

            ).collect(Collectors.toList());

        }
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        voteRecordService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody VoteRecord voteRecord)
    {
        voteRecordService.update(voteRecord);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody VoteRecord voteRecord)
    {
        voteRecordService.save(voteRecord);
        return ResultVM.success(null);
    }
}
