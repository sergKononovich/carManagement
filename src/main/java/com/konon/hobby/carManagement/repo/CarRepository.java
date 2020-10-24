package com.konon.hobby.carManagement.repo;

import com.konon.hobby.carManagement.model.Car;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

    Optional<Car> findByCarNumber(String carNumber);
}
