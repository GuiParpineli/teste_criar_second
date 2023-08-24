package com.demo.demo.repository;

import com.demo.demo.model.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer> {
    Piloto findByName(String name);
}
