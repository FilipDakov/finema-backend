package com.fd.finema.repository;

import com.fd.finema.bom.Reservation;
import com.fd.finema.bom.Screening;
import com.fd.finema.bom.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Optional<List<Reservation>> findAllByScreeningAndStatusIsNot(Screening screening, Status status);
    Optional<Reservation> findFirstByScreeningAndSeatNumber(Screening screening,Integer seat);
}
