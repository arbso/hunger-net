package com.lufthansa.backend.repository;

import com.lufthansa.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {

    // query me sql
    @Query(value="SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=:id;", nativeQuery = true)
    List<User> findUserByRoles(@Param("id") Integer id);

    @Query(value="SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles !=2",nativeQuery = true)
    List<User> findAllUsersExceptAdmins();

    @Query(value="SELECT * FROM user\n" +
            "WHERE user.active = true",nativeQuery = true)
    List<User> findActiveUsers();

    @Modifying
    @Query("UPDATE User u SET u.active = false")
    User makeUserInactive(@Param("id") Integer id);

    @Query(value="SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=0;", nativeQuery = true)
    List<User> findClientUsers();

    @Query(value="SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=1;", nativeQuery = true)
    List<User> findManagerUsers();

    @Query(value="SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=2;", nativeQuery = true)
    List<User> findAdminUsers();


    User findByUsername(String username);
    boolean existsByUsername(String username);
}
