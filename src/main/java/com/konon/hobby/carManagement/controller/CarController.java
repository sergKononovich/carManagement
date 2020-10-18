package com.konon.hobby.carManagement.controller;

import com.konon.hobby.carManagement.model.Car;
import com.konon.hobby.carManagement.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping()
    public void testDatabase(Model model) {
        carRepository.save(new Car("1234-AB"));
        carRepository.save(new Car("2222-DF"));
        carRepository.save(new Car("5634-GF"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable long id) {

        Optional<Car> optionalCar = carRepository.findById(id);

        if(optionalCar.isPresent())
            return new ResponseEntity<>(optionalCar.get(), HttpStatus.OK);

        return new ResponseEntity("CAR NOT FOUND", HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity createCar(@RequestBody Car car) {
        return new ResponseEntity(carRepository.save(car).getId(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        carRepository.deleteById(id);
        return new ResponseEntity(carRepository.existsById(id), HttpStatus.OK);
    }


    @PostMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable long id, @RequestBody Car car) {
        car.setId(id);
        carRepository.save(car);
        return new ResponseEntity("SAVED", HttpStatus.OK);
    }
}
