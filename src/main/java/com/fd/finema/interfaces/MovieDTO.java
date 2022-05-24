package com.fd.finema.interfaces;

import com.fd.finema.bom.AgeRestriction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MovieDTO {
    private String name;
    private AgeRestriction ageRestriction;
    private Integer timespan;
    private String description;
    private LocalDate releaseDate;
    private String imgPath;
    private List<String> genres;
    private List<ActorDTO> actors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Integer getTimespan() {
        return timespan;
    }

    public void setTimespan(Integer timespan) {
        this.timespan = timespan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
    }
}
