package com.example.springbootauthdemo.medicaltest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalSubTestRepository extends JpaRepository<MedicalSubTest, Long> {

}
