package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.service.VoteRecordService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteRecordService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<VoteRecord> list(@PathVariable("id") String id)
    {
        VoteRecord voteRecord = voteRecordService.findById(id);

        return ResultVO.success(voteRecord);
    }


    @GetMapping("/all")
    public ResultVO<List<VoteRecord>> getAll()
    {
        List<VoteRecord> result = voteRecordService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        voteRecordService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody VoteRecord voteRecord)
    {
        voteRecordService.update(voteRecord);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody VoteRecord voteRecord)
    {
        voteRecordService.save(voteRecord);
        return ResultVO.success(null);
    }
}
