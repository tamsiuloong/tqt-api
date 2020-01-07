package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.service.TrackService;
import com.coachtam.tqt.to.TrackForm;
import com.coachtam.tqt.vo.admin.ResultVO;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize,@RequestBody TrackForm searchForm)
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
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Track> list(@PathVariable("id") String id)
    {
        Track track = trackService.findById(id);

        return ResultVO.success(track);
    }


    @GetMapping("/all")
    public ResultVO<List<Track>> getAll()
    {
        List<Track> result = trackService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        trackService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Track track)
    {
        trackService.update(track);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Track track)
    {
        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        track.setCreateBy(user.getUsername());
        track.setCreateTime(new Date());
        trackService.save(track);
        return ResultVO.success(null);
    }
}
