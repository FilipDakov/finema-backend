package com.fd.finema.bom;

public enum Day {
	MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("THURSDAY"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");

	private String value;

	Day(String day) {
		this.value = day;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
