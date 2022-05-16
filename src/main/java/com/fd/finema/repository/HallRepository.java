package com.fd.finema.repository;

import com.fd.finema.bom.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall,Long> {
    Optional<Hall> findByNumber(Integer number);
}
