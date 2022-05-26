package com.fd.finema.bom;

public enum ScreeningPremiereType {
    BASIC("Обикновен"), PREMIERE("Премиера"),PRE_PREMIERE("Предпремиера");

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
