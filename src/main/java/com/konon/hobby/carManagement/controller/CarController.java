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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCar(@RequestBody Car car) {

        Optional<Car> carInDatabase = carRepository.findByCarNumber(car.getCarNumber());

        if(!carInDatabase.isPresent())
            return new ResponseEntity(carRepository.save(car).getCarId(), HttpStatus.CREATED);

        return new ResponseEntity("CAR with number = " + car.getCarNumber() + " IS EXIST", HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCar(@PathVariable long id) {
        if(carRepository.existsById(id)) {
            carRepository.deleteById(id);

            return new ResponseEntity("CAR with ID = " + id + " DELETED", HttpStatus.OK);
        }

        return new ResponseEntity("CAR with ID = " + id + " NOT EXIST", HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCar(@PathVariable long id, @RequestBody Car car) {
        if(carRepository.existsById(id))
        {
            car.setCarId(id);
            carRepository.save(car);
            return new ResponseEntity(car.getCarId(), HttpStatus.OK);
        }

        return new ResponseEntity("Car with ID " + id + " NOT FOUND", HttpStatus.NOT_FOUND);
    }
}
