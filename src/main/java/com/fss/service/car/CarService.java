package com.fss.service.car;

import com.fss.domainobject.CarDO;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import java.util.List;
import java.util.Map;

public interface CarService
{

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    List<CarDO> findAll();

    CarDO update(CarDO carDO) throws EntityNotFoundException;

    List<CarDO> findCars(Map<String, String> requestParams);
}
