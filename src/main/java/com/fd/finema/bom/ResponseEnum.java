package com.fd.finema.bom;

public enum ResponseEnum {

    ERROR("ERROR"), SUCCESS("SUCCESS"), WARNING("WARNING");


    private String value;


    ResponseEnum(String response) {
        this.value = response;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
