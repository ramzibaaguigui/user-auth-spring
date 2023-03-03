package com.example.springbootauthdemo.auth.repository;

import com.example.springbootauthdemo.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    @Query("select u from UserAuth u where u.token = ?1")
    Optional<UserAuth> findUserAuthByTokenEquals(String authToken);
}
