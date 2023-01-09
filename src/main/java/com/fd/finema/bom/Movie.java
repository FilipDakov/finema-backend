package com.fd.finema.bom;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
@Table
public class Movie extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = " varchar(32) default 'G'")
    @Enumerated(value = EnumType.STRING)
    private AgeRestriction ageRestriction;

    @Column(nullable = false)
    private Integer timespan;

    @Column(nullable = false,length = 3000)
    private String description;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String imgPath;

    @Column(name = "active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

    public Movie() {
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

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

    public List<Genre> getGenres() {
        if(genres == null){
            genres = new ArrayList<>();
        }
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
//
//    public List<Screening> getScreenings() {
//        if(screenings == null){
//            screenings = new ArrayList<>();
//        }
//        return screenings;
//    }
//
//    public void setScreenings(List<Screening> screenings) {
//        this.screenings = screenings;
//    }
}
