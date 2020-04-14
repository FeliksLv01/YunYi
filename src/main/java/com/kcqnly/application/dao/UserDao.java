package com.kcqnly.application.dao;

import com.kcqnly.application.entity.Role;
import com.kcqnly.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
    User findByUsername( String username);

    User save(User user);


    List<User> findByUsernameLike(String name);

    @Modifying
    @Query(value="UPDATE user SET state=:state WHERE id=:id",nativeQuery = true)
    @Transactional
    void updateState(@Param("id") int id, @Param("state") boolean state);

    void deleteById(int id);

    @Modifying
    @Query(value="UPDATE user SET mobile=:mobile, email=:email WHERE id=:id",nativeQuery = true)
    @Transactional
    void updateInformation(@Param("id") int id, @Param("mobile") String mobile,@Param("email") String email);

    @Modifying
    @Query(value="UPDATE user SET role_id=:rid WHERE id=:id",nativeQuery = true)
    @Transactional
    void updateRole(@Param("id")int id,@Param("rid") int rid);
}
