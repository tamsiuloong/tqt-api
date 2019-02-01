package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleDao extends JpaRepository<Module,String> {
    @Modifying
    @Query(value = "delete from role_module_p where module_id = ?1",nativeQuery = true)
    void deleteRelationship(String id);

    @Query("from Module where layerNum = ?1")
    List<Module> getListByLayerNum(Long layerNum);
}
