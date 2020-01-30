package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Offer;
import com.coachtam.tqt.service.OfferService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	入职邀请
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-10-23 9:45:45
 */
@RequestMapping("/api/offer")
@RestController
public class OfferCtrl {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = offerService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Offer> list(@PathVariable("id") Long id)
    {
        Offer offer = offerService.findById(id);

        return ResultVM.success(offer);
    }


    @GetMapping("/all")
    public ResultVM<List<Offer>> getAll()
    {
        List<Offer> result = offerService.findAll();
        return ResultVM.success(result);
    }


    @GetMapping("/uid/{uid}")
    public ResultVM<List<Offer>> queryListByUid(@PathVariable("uid")String uid)
    {
        List<Offer> result = offerService.findListByUid(uid);
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody Long[] ids)
    {
        offerService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody Offer offer)
    {
        offerService.update(offer);
        return ResultVM.success(null);
    }

    @PutMapping("entry/{id}/{isEntry}")
    public ResultVM<String> updateEntry(@PathVariable("id")Long id, @PathVariable("isEntry")Boolean isEntry)
    {
        Offer offer  = offerService.findById(id);
        offer.setIsEntry(isEntry);
        offerService.update(offer);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody Offer offer)
    {
        offerService.save(offer);
        return ResultVM.success(null);
    }
}
