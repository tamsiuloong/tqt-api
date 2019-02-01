package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleDao extends JpaRepository<Module,String> {
    @Modifying
    @Query(value = "delete from role_module_p where module_id = ?1",nativeQuery = true)
    void deleteRelationship(String id);
}
