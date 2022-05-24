package com.fd.finema.services;

import com.fd.finema.interfaces.ScreeningDTO;

public interface ScreeningService {
    public void createScreening(ScreeningDTO screeningDTO);

    void getCurrentWeekScreenings();
}
