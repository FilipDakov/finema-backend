package com.fd.finema.repository;

import com.fd.finema.bom.Reservation;
import com.fd.finema.bom.Screening;
import com.fd.finema.bom.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Optional<List<Reservation>> findAllByScreeningAndStatusIsNot(Screening screening, Status status);
    Optional<Reservation> findFirstByScreeningAndSeatNumber(Screening screening,Integer seat);
    Optional<List<Reservation>> findAllByFirstNameAndMiddleNameAndLastNameAndStatus(String fName,String mName,String lName,Status status);
    Optional<List<Reservation>> findAllByUser_EmailOrderByTimestampDesc(String username);
    Optional<List<Reservation>> findAllByStatus(Status status);

    @Query(value = "SELECT r FROM Reservation r " +
            "WHERE ( ?1 IS NULL OR r.firstName = ?1) " +
            "AND (?2 IS NULL OR r.middleName = ?2) " +
            "AND (?3 IS NULL OR r.lastName = ?3) " +
            "AND r.status = ?4")
    Optional<List<Reservation>> findAllByNames(String fName,String mName,String lName,Status status);
}
