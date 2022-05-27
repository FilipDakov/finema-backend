package com.fd.finema.repository;

import com.fd.finema.bom.Hall;
import com.fd.finema.bom.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    Optional<List<Screening>> getAllByDateAndHall(LocalDate date, Hall hall);
    Optional<List<Screening>> getAllByDateAfterAndDateBefore(LocalDate monday,LocalDate sunday);
    Optional<Screening> findFirstByDateAndStartTimeAndHall_NumberAndMovie_Name(LocalDate date,String startTime,Integer hall,String movieName);
    Optional<Screening> findFirstByStartTimeAndHall_NumberAndMovie_Name(String startTime,Integer hall,String movieName);

}
