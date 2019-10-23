package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferDao extends JpaRepository<Offer,Long> {
    List<Offer> findAllByUserId(String uid);
}
