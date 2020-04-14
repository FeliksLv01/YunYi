package com.kcqnly.application.dao;

import com.kcqnly.application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    Role save(Role role);

    @Modifying
    @Query(value="UPDATE role SET name=:name , description=:description WHERE id=:id",nativeQuery = true)
    @Transactional
    void updateRole(@Param("id") int id, @Param("name") String name, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "DELETE from role_permissions where role_id=:id and permissions_id=:rightId",nativeQuery = true)
    void deletePermissionById(@Param("id") int id,@Param("rightId") int rightId);


}
