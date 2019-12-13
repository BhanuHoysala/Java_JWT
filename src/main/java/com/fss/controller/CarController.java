package com.fss.controller;

import com.fss.controller.mapper.CarMapper;
import com.fss.datatransferobject.CarDTO;
import com.fss.domainobject.CarDO;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import com.fss.service.car.CarService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Operations associated with Cars routed by this controller.
 */
@RestController
@RequestMapping("v1/cars")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CarController
{

    CarService carService;


    @GetMapping
    public List<CarDTO> findAll()
    {

        return CarMapper.makeCarDTOList(carService.findAll());
    }


    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable Long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
    {
        CarDO CarDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(CarDO));
    }


    @PutMapping("/{carId}")
    public CarDTO updateCar(@PathVariable Long carId, @Valid @RequestBody CarDTO carDTO) throws EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        carDO.setId(carId);
        carService.update(carDO);
        return CarMapper.makeCarDTO(carDO);
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }


    /**
     * REST rout which is scalable to any number Parameters
     *
     * @param queryParams
     * @return
     */
    @GetMapping("/search")
    public List<CarDTO> findCars(@RequestParam Map<String, String> queryParams)
    {

        if (queryParams.isEmpty())
        {
            return new ArrayList<>();
        }
        // TODO - validate the queryParams
        return CarMapper.makeCarDTOList(carService.findCars(queryParams));
    }
}
