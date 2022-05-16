package com.fd.finema.bom;

public enum Status {
    NEW("New"), DELETED("Deleted"), CONFIRMED("Confirmed");
    private String value;


    Status(String status) {
        this.value =status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
