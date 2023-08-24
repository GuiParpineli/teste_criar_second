package com.demo.demo.repository;

import com.demo.demo.model.Lap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoltaRepository extends JpaRepository<Lap, Integer> {
}
