package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.service.TrackService;
import com.coachtam.tqt.to.TrackForm;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-14 15:41:24
 */
@RequestMapping("/api/track")
@RestController
public class TrackCtrl {

    @Autowired
    private TrackService trackService;

    @PostMapping("/search")
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, @RequestBody TrackForm searchForm)
    {
        Page result = trackService.page(pageNo,pageSize,(root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(searchForm.getClassId()!=null && !searchForm.getClassId().isEmpty() &&!"all".equals(searchForm.getClassId()))
            {
                Join<Feedback, Classes> joins = root.join("user").join("classes");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getClassId());

                predicates.add(equal);
            }
            if(searchForm.getStuName()!=null && !searchForm.getStuName().isEmpty())
            {
                Join<Feedback, UserInfo> joins = root.join("user").join("userInfo");
                Predicate equal = builder.equal(joins.get("name"), searchForm.getStuName());

                predicates.add(equal);
            }


            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Track> list(@PathVariable("id") String id)
    {
        Track track = trackService.findById(id);

        return ResultVM.success(track);
    }


    @GetMapping("/all")
    public ResultVM<List<Track>> getAll()
    {
        List<Track> result = trackService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        trackService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody Track track)
    {
        trackService.update(track);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody Track track)
    {
        com.coachtam.tqt.utils.jwt.UserInfo user = LoginInterceptor.getCurrUser();
        track.setCreateBy(user.getUsername());
        track.setCreateTime(new Date());
        trackService.save(track);
        return ResultVM.success(null);
    }
}
