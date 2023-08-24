package com.demo.demo.repository;

import com.demo.demo.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotoRepository extends JpaRepository<Pilot, Integer> {
    Pilot findByName(String name);
}
