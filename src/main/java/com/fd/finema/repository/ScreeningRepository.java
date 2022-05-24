package com.fd.finema.repository;

import com.fd.finema.bom.Hall;
import com.fd.finema.bom.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    Optional<List<Screening>> getAllByDateAndHall(Date date, Hall hall);
    Optional<List<Screening>> getAllByDateAfterAndDateBefore(Date monday,Date sunday);
}
