package com.konon.hobby.carManagement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Driver {

    @Id
    @GeneratedValue
    private long driverId;

    private String firstName;
    private String lastName;

    public Driver() {

    }

    public Driver(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
