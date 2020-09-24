package com.konon.hobby.carManagement.controller;

import com.konon.hobby.carManagement.model.Car;
import com.konon.hobby.carManagement.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/car")
    public void testDatabase(Model model) {
        carRepository.save(new Car("1234-AB"));
        carRepository.save(new Car("2222-DF"));
        carRepository.save(new Car("5634-GF"));
    }

}
