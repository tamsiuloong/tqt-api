package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.VoteItem;
import com.coachtam.tqt.service.VoteItemService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
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
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = voteItemService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<VoteItem> list(@PathVariable("id") String id)
    {
        VoteItem voteItem = voteItemService.findById(id);

        return ResultVM.success(voteItem);
    }


    @GetMapping("/all")
    public ResultVM<List<VoteItem>> getAll()
    {
        List<VoteItem> result = voteItemService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        voteItemService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody VoteItem voteItem)
    {
        voteItemService.update(voteItem);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody VoteItem voteItem)
    {
        voteItemService.save(voteItem);
        return ResultVM.success(null);
    }
}
