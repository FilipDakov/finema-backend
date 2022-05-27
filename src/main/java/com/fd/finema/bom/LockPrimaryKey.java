package com.fd.finema.bom;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LockPrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(targetEntity = Screening.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "screening_id",referencedColumnName = "id")
    private Screening screening;

    private Integer seatNumber;

    public LockPrimaryKey(Screening screening, Integer seatNumber) {
        this.screening = screening;
        this.seatNumber = seatNumber;
    }

    public LockPrimaryKey() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockPrimaryKey that = (LockPrimaryKey) o;
        return Objects.equals(screening, that.screening) && Objects.equals(seatNumber, that.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screening, seatNumber);
    }
}
