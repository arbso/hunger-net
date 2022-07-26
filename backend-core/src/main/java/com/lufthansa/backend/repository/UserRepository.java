package com.lufthansa.backend.repository;

import com.lufthansa.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    // Query to find users with a specific role
    @Query(value = "SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=:id;", nativeQuery = true)
    List<User> findUserByRoles(@Param("id") Integer id);

    // Query to filter out the admins in a user list
    @Query(value = "SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles !=2", nativeQuery = true)
    List<User> findAllUsersExceptAdmins();

    // Query to filter out inactive users
    // Replaced with proper Spring Annotations
    @Query(value = "SELECT * FROM user\n" +
            "WHERE user.active = true", nativeQuery = true)
    List<User> findActiveUsers();

    // Query to make a user inactive
    // Replaced with a soft-delete implementation

    @Modifying
    @Query("UPDATE User u SET u.active = false")
    User makeUserInactive(@Param("id") Integer id);

    @Query(value = "SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=0;", nativeQuery = true)
    List<User> findClientUsers();

    @Query(value = "SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=1;", nativeQuery = true)
    List<User> findManagerUsers();

    @Query(value = "SELECT user.* FROM user INNER JOIN user_roles ON user.user_id = user_roles.user_user_id WHERE user_roles.roles=2;", nativeQuery = true)
    List<User> findAdminUsers();

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
