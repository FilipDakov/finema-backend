package com.fd.finema.bom;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Screening extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Day dayOfWeek;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String startTime;

    @ManyToOne(targetEntity = Hall.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;

    @Column(nullable = false)
    private BigDecimal ticketCost;

    @Enumerated(value = EnumType.STRING)
    private ScreeningTypeEnum screeningType;

    @Enumerated(value = EnumType.STRING)
    private ScreeningPremiereType premiereType;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

//    @OneToMany(mappedBy = "screening")
//    private List<Reservation> reservations;


    public ScreeningPremiereType getPremiereType() {
        return premiereType;
    }

    public void setPremiereType(ScreeningPremiereType premiereType) {
        this.premiereType = premiereType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public BigDecimal getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(BigDecimal ticketCost) {
        this.ticketCost = ticketCost;
    }

    public ScreeningTypeEnum getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(ScreeningTypeEnum screeningType) {
        this.screeningType = screeningType;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
