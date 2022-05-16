package com.fd.finema.interfaces;

import com.fd.finema.bom.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

public class ScreeningDTO{
    private Date date;
    private String startTime;
    private Integer hall;
    private BigDecimal ticketCost;
    private ScreeningTypeEnum screeningType;
    private String movie;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
