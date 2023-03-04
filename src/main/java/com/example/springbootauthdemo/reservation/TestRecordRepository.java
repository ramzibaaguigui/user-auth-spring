package com.example.springbootauthdemo.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRecordRepository extends JpaRepository<TestRecord, Long> {
}
