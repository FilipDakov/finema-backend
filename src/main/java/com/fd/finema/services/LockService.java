package com.fd.finema.services;

import com.fd.finema.interfaces.LockDTO;
import org.springframework.transaction.annotation.Transactional;

public interface LockService {
    public void unlockExpiredSeats();

    @Transactional
    void lockSeat(LockDTO lockDto);

    @Transactional
    void unlockSeat(LockDTO lockDto);
}
