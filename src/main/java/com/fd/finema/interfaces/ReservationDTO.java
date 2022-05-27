package com.fd.finema.interfaces;

import com.fd.finema.bom.Seat;

import java.util.List;

public class ReservationDTO {
    private ScreeningDTO screening;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<Integer> seatNumbers;
    private String user;


    public ReservationDTO(ScreeningDTO screening, String firstName, String middleName, String lastName, List<Integer> seatNumbers, String user) {
        this.screening = screening;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.seatNumbers = seatNumbers;
        this.user = user;
    }

    public ReservationDTO(){}

    public ScreeningDTO getScreening() {
        return screening;
    }

    public void setScreening(ScreeningDTO screening) {
        this.screening = screening;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<Integer> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
