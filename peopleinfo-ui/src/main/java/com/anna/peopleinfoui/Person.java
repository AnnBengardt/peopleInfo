package com.anna.peopleinfoui;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final StringProperty city;
    private final IntegerProperty postalCode;
    private final ObjectProperty<LocalDate> birthday;

    public Person(){
        this(null, null);
    }
    public Person(String firstName, String lastName){
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        this.street = new SimpleStringProperty("Prospekt Mira");
        this.city = new SimpleStringProperty("Moscow");
        this.postalCode = new SimpleIntegerProperty(101067);
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(2001, 01, 01));
    }


    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getCity() {
        return city.get();
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }


    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }


    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    public StringProperty getLastNameProperty() {
        return lastName;
    }

    public StringProperty getStreetProperty() {
        return street;
    }

    public StringProperty getCityProperty() {
        return city;
    }

    public IntegerProperty getPostalCodeProperty() {
        return postalCode;
    }

    public ObjectProperty<LocalDate> getBirthdayProperty() {
        return birthday;
    }
}