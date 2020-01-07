package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.VoteItem;
import com.coachtam.tqt.service.VoteItemService;
import com.coachtam.tqt.vo.admin.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	投票项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:59
 */
@RequestMapping("/api/voteItem")
@RestController
public class VoteItemCtrl {

    @Autowired
    private VoteItemService voteItemService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteItemService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<VoteItem> list(@PathVariable("id") String id)
    {
        VoteItem voteItem = voteItemService.findById(id);

        return ResultVO.success(voteItem);
    }


    @GetMapping("/all")
    public ResultVO<List<VoteItem>> getAll()
    {
        List<VoteItem> result = voteItemService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        voteItemService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody VoteItem voteItem)
    {
        voteItemService.update(voteItem);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody VoteItem voteItem)
    {
        voteItemService.save(voteItem);
        return ResultVO.success(null);
    }
}
