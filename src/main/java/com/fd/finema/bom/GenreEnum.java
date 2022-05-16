package com.fd.finema.bom;

public enum GenreEnum {
    ACTION("ЕКШЪН"), COMEDY("КОМЕДИЯ"), SCIFI("SCIENCE-FICTION"),FANTASY("ФЕНТЪЗИ"),DRAMA("ДРАМА");

    private String value;

    GenreEnum(String genre) {
        this.value = genre;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
