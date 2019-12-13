package com.fss.service.car;

import com.fss.dataaccessobject.CarRepository;
import com.fss.dataaccessobject.CarRepositoryCustom;
import com.fss.domainobject.CarDO;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarServiceImpl implements CarService
{

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    CarRepository carRepository;

    CarRepositoryCustom carRepositoryCustom;


    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }


    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find car entity with id: " + carId));
    }


    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException
    {
        try
        {
            return carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
    }


    @Override
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
        carRepository.save(carDO);
    }


    @Override
    public List<CarDO> findAll()
    {
        return carRepository.findAll();
    }


    @Override
    public CarDO update(CarDO carDO) throws EntityNotFoundException
    {
        carRepository.findById(carDO.getId());
        return carRepository.save(carDO);
    }


    @Override
    public List<CarDO> findCars(Map<String, String> requestParams)
    {
        return carRepositoryCustom.findCars(requestParams);
    }
}
