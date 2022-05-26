package com.fd.finema.interfaces;

import java.util.ArrayList;
import java.util.List;

public class MovieScreeningDTO {

    private String movie;
    private List<ScreeningDTO> screenings;

    public MovieScreeningDTO(String movie, List<ScreeningDTO> screenings) {
        this.movie = movie;
        this.screenings = screenings;
    }

    public MovieScreeningDTO() {
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public List<ScreeningDTO> getScreenings() {
        if (this.screenings == null) {
            this.screenings = new ArrayList<>();
        }
        return screenings;
    }

    public void setScreenings(List<ScreeningDTO> screenings) {
        this.screenings = screenings;
    }
}
