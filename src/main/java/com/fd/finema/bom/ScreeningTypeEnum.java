package com.fd.finema.bom;

public enum ScreeningTypeEnum {
    D2("2D"), D3("3D") , DX4("4DX"), D8("8D"),DUB("DUB");

    private String value;


    ScreeningTypeEnum(String dimension) {
        this.value = dimension;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
