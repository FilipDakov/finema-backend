package com.fd.finema.services;

import com.fd.finema.interfaces.MovieScreeningDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScreeningService {
    public void createScreening(ScreeningDTO screeningDTO);

//    List<ScreeningDTO> getCurrentWeekScreenings();
    List<MovieScreeningDTO> getCurrentScreeningsForDay(String day);
    String getTodayDay();
}
