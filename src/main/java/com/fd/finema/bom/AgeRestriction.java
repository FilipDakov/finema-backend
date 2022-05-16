package com.fd.finema.bom;

public enum AgeRestriction {
    R("Restricted"), NC17("Adults only"), G("General audience"), PG("Parental guidance"), PG13("Parents cautioned");

    private String value;

    AgeRestriction(String type) {
        this.value = type;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
