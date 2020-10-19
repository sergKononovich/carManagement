package com.konon.hobby.carManagement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue
    private Long carId;

    private String carNumber;
//    private String name;
//    private String description;

    public Car() {
    }

    public Car(String carNumber) {
        this.carNumber = carNumber;
    }

}
