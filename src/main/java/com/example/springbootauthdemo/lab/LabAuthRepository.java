package com.example.springbootauthdemo.lab;

import com.example.springbootauthdemo.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LabAuthRepository extends JpaRepository<LabAuth, Long> {

    @Query("select l from LabAuth l where l.token = ?1")
    Optional<LabAuth> findLabAuthByTokenEquals(String authToken);
}
