package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Offer;
import com.coachtam.tqt.service.OfferService;
import com.coachtam.tqt.vo.ResultVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = offerService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Offer> list(@PathVariable("id") Long id)
    {
        Offer offer = offerService.findById(id);

        return ResultVO.success(offer);
    }


    @GetMapping("/all")
    public ResultVO<List<Offer>> getAll()
    {
        List<Offer> result = offerService.findAll();
        return ResultVO.success(result);
    }


    @GetMapping("/uid/{uid}")
    public ResultVO<List<Offer>> queryListByUid(@PathVariable("uid")String uid)
    {
        List<Offer> result = offerService.findListByUid(uid);
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody Long[] ids)
    {
        offerService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Offer offer)
    {
        offerService.update(offer);
        return ResultVO.success(null);
    }

    @PutMapping("entry/{id}/{isEntry}")
    public ResultVO<String> updateEntry(@PathVariable("id")Long id,@PathVariable("isEntry")Boolean isEntry)
    {
        Offer offer  = offerService.findById(id);
        offer.setIsEntry(isEntry);
        offerService.update(offer);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Offer offer)
    {
        offerService.save(offer);
        return ResultVO.success(null);
    }
}
