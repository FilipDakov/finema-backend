package com.fd.finema.bom;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class SeatLocker {

    @EmbeddedId
    private LockPrimaryKey id;

    private String user;

    @CreationTimestamp
    private Timestamp timestamp;


    public SeatLocker(LockPrimaryKey id, String user, Timestamp timestamp) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
    }

    public SeatLocker() {
    }

    public LockPrimaryKey getId() {
        return id;
    }

    public void setId(LockPrimaryKey id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
