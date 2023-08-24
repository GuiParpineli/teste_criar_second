package com.demo.demo.repository;

import com.demo.demo.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvaRepository extends JpaRepository<Race, Integer> {
}
