package com.fd.finema.bom;

public enum ScreeningPremiereType {
    BASIC("BASIC"), PREMIERE("PREMIERE"),PRE_PREMIERE("PRE PREMIERE");

    private String value;


    ScreeningPremiereType(String dimension) {
        this.value = dimension;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
