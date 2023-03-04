package com.example.springbootauthdemo.lab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Laboratory, Long> {
    Optional<Laboratory> findLabByUsernameEquals(String username);
}
