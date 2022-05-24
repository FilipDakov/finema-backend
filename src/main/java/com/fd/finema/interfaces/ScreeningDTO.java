package com.fd.finema.interfaces;

import com.fd.finema.bom.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class ScreeningDTO{
    private LocalDate date;
    private String startTime;
    private Integer hall;
    private BigDecimal ticketCost;
    private ScreeningTypeEnum screeningType;
    private ScreeningPremiereType premiereType;
    private String movie;
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ScreeningPremiereType getPremiereType() {
        return premiereType;
    }

    public void setPremiereType(ScreeningPremiereType premiereType) {
        this.premiereType = premiereType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getHall() {
        return hall;
    }

    public void setHall(Integer hall) {
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

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
