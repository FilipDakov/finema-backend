package com.fd.finema.interfaces;

public class LockDTO {

    private ScreeningDTO screening;
    private String user;
    private Integer seatNumber;


    public LockDTO(ScreeningDTO screening, String user, Integer seatNumber) {
        this.screening = screening;
        this.user = user;
        this.seatNumber = seatNumber;
    }

    public LockDTO() {
    }

    public ScreeningDTO getScreening() {
        return screening;
    }

    public void setScreening(ScreeningDTO screening) {
        this.screening = screening;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
