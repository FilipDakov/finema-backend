package com.fd.finema.services;

import com.fd.finema.bom.LockPrimaryKey;
import com.fd.finema.bom.Screening;
import com.fd.finema.bom.SeatLocker;
import com.fd.finema.interfaces.LockDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.repository.LockRepository;
import com.fd.finema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LockServiceImpl implements LockService {

    LockRepository lockRepo;
    ScreeningRepository screeningRepository;

    public LockServiceImpl(LockRepository lockRepo, ScreeningRepository screeningRepository) {
        this.lockRepo = lockRepo;
        this.screeningRepository = screeningRepository;
    }

    @Override
    @Transactional
    public void unlockExpiredSeats() {
        Optional<List<SeatLocker>> expired = lockRepo.findByTimestampBefore(Timestamp.valueOf(LocalDateTime.now().minusMinutes(5)));
        expired.get().forEach(lock -> lockRepo.delete(lock));
    }

    @Transactional
    @Override
    public synchronized void lockSeat(LockDTO lockDto) {
        unlockExpiredSeats();
        LockPrimaryKey pk = createLockPrimaryKey(lockDto);
        Optional<SeatLocker> lockerOptional = lockRepo.findById(pk);
        if(lockerOptional.isPresent()){
            if(!lockerOptional.get().getUser().equals(lockDto.getUser())){
                throw new IllegalArgumentException("Locked by another user");
            }else{
                lockerOptional.get().setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            }
        }

        lockRepo.save(new SeatLocker(pk,lockDto.getUser(),Timestamp.valueOf(LocalDateTime.now())));

    }


    @Transactional
    @Override
    public synchronized void unlockSeat(LockDTO lockDto) {

        LockPrimaryKey pk = createLockPrimaryKey(lockDto);
        Optional<SeatLocker> lockerOptional = lockRepo.findById(pk);
        if(!lockerOptional.isPresent()){
            throw new IllegalArgumentException("No such lock!");
        }
        if(!lockerOptional.get().getUser().equals(lockDto.getUser())){
            throw new IllegalArgumentException("Seat locked by another user!");
        }
        lockRepo.delete(lockerOptional.get());
        lockRepo.flush();
        unlockExpiredSeats();
    }


    private synchronized LockPrimaryKey createLockPrimaryKey(LockDTO lockDto){
        ScreeningDTO dtoScreening = lockDto.getScreening();
        if (dtoScreening == null) {
            throw new IllegalArgumentException("Screening should not be null");
        }
        Optional<Screening> screening = screeningRepository.findFirstByDateAndStartTimeAndHall_NumberAndMovie_Name(dtoScreening.getDate(), dtoScreening.getStartTime(),
                dtoScreening.getHall(), dtoScreening.getMovie());

        if (!screening.isPresent()) {
            throw new IllegalArgumentException("Screening is not found");
        }

        return new LockPrimaryKey(screening.get(), lockDto.getSeatNumber());
    }
}
