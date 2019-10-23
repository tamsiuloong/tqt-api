package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Offer;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	入职邀请
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-10-23 9:45:45
 */
public interface OfferService {
    Page<Offer> page(Integer pageNo, Integer pageSize);

    List<Offer> findAll();

    void save(Offer model);

    Offer findById(Long id);

    void update(Offer model);

    void deleteByIds(Long[] id);

    List<Offer> findListByUid(String uid);
}
