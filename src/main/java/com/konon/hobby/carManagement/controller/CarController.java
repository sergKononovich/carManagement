package com.konon.hobby.carManagement.controller;

import com.konon.hobby.carManagement.model.Car;
import com.konon.hobby.carManagement.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCar(@PathVariable long id) {

        Optional<Car> optionalCar = carRepository.findById(id);

        if(optionalCar.isPresent())
            return new ResponseEntity<>(optionalCar.get(), HttpStatus.OK);

        return new ResponseEntity("CAR with ID = " + id + " NOT FOUND", HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity createCar(@RequestBody Car car) {

        return new ResponseEntity(carRepository.save(car).getCarId(), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        carRepository.deleteById(id);
        return new ResponseEntity(carRepository.existsById(id), HttpStatus.OK);
    }


    @PostMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable long id, @RequestBody Car car) {
        car.setCarId(id);
        carRepository.save(car);
        return new ResponseEntity(car.getCarId(), HttpStatus.OK);
    }
}
