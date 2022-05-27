package com.fd.finema.repository;

import com.fd.finema.bom.LockPrimaryKey;
import com.fd.finema.bom.Screening;
import com.fd.finema.bom.SeatLocker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockRepository extends JpaRepository<SeatLocker,LockPrimaryKey> {
    Optional<SeatLocker> findById(LockPrimaryKey pk);
//    Optional<SeatLocker> findByI
    Optional<List<SeatLocker>> findByTimestampBefore(Timestamp timestamp);
    Optional<List<SeatLocker>> findAllById_Screening(Screening screening);
}
