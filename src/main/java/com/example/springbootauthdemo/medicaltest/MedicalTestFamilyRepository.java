package com.example.springbootauthdemo.medicaltest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestFamilyRepository extends JpaRepository<MedicalTestFamily, Long> {

}
