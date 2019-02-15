package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface TrackDao extends JpaRepository<Track,String>, JpaSpecificationExecutor<Track> {
}
