package com.konon.hobby.carManagement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Car {

    public Car() {
    }

    public Car(String number) {
        this.number = number;
    }

    @Id
    @GeneratedValue
    private long id;

    private String number;
//    private String name;
//    private String description;
}
