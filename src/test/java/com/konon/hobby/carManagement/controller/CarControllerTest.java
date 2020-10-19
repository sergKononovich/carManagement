package com.konon.hobby.carManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konon.hobby.carManagement.model.Car;
import com.konon.hobby.carManagement.repo.CarRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarRepository carRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @AfterEach
    public void afterEach() {
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void shouldGetCarById() throws Exception {
        Car testCar = createTestCar(1L);

        when(carRepository.findById(testCar.getCarId())).thenReturn(Optional.of(testCar));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/car/" + testCar.getCarId())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("carId", Matchers.is(testCar.getCarId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("carNumber"
                        , Matchers.is("NUMBER " + testCar.getCarId())));

        verify(carRepository).findById(testCar.getCarId());
    }

    @Test
    void shouldGetCarNotFound() throws Exception {
        Long notExistCarId = 4L;

        when(carRepository.findById(notExistCarId)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/car/" + notExistCarId)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string("CAR with ID = " + notExistCarId + " NOT FOUND"));

        verify(carRepository).findById(notExistCarId);

    }

    @Test
    void shouldCreateCar() throws Exception {
        Car testCar = createTestCar(3L);

        when(carRepository.save(testCar)).thenReturn(testCar);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/car")
                        .content(objectMapper.writeValueAsString(testCar))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(testCar.getCarId().toString()));

        verify(carRepository).save(testCar);
    }

    void shouldGetCarIsExist() {
        //Авто с такими параметрами уже существует.
    }

    @Test
    void shouldDeleteCar() throws Exception {
        Long deleteCarId = 3L;
        Boolean existCar = false;

        when(carRepository.existsById(deleteCarId)).thenReturn(existCar);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/car/delete/" + deleteCarId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(existCar.toString()));

        verify(carRepository).deleteById(deleteCarId);
        verify(carRepository).existsById(deleteCarId);
    }

    void shouldNotDeleteCar() {
        //Нельзя удалить то, чего нет.
    }

    @Test
    void shouldUpdateCar() throws Exception {
        Car testCar = createTestCar(4L);

        when(carRepository.save(testCar)).thenReturn(testCar);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/car/" + testCar.getCarId())
                        .content(objectMapper.writeValueAsString(testCar))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(testCar.getCarId().toString()));

        verify(carRepository).save(testCar);

    }

    void shouldNotUpdateCar() {
        //Нельзя обновить несущестующий автомобиль.
    }

    private Car createTestCar(Long index)
    {
        Car car = new Car();

        car.setCarId(index);
        car.setCarNumber("NUMBER " + index);
        return car;
    }
}