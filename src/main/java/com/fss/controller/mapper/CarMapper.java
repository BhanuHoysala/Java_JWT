package com.fss.controller.mapper;

import com.fss.datatransferobject.CarDTO;
import com.fss.domainobject.CarDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarMapper
{

    public static CarDTO makeCarDTO(CarDO carDO)
    {

        CarDTO carDTO = new CarDTO();

        carDTO.setId(carDO.getId());
        carDTO.setConvertible(carDO.isConvertible());
        carDTO.setEngineType(carDO.getEngineType());
        carDTO.setLicensePlate(carDO.getLicensePlate());
        carDTO.setSeatCount(carDO.getSeatCount());
        carDTO.setRating(carDO.getRating());

        return carDTO;
    }


    public static CarDO makeCarDO(CarDTO carDTO)
    {

        CarDO carDO = new CarDO();
        carDO.setConvertible(carDTO.isConvertible());
        carDO.setEngineType(carDTO.getEngineType());
        carDO.setLicensePlate(carDTO.getLicensePlate());
        carDO.setSeatCount(carDTO.getSeatCount());
        carDO.setRating(carDTO.getRating());

        return carDO;
    }


    public static List<CarDTO> makeCarDTOList(List<CarDO> cars)
    {
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
}
