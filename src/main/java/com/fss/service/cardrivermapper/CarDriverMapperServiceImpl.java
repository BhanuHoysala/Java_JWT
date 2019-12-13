package com.fss.service.cardrivermapper;

import com.fss.dataaccessobject.CarDriverMapperRepository;
import com.fss.domainobject.CarDO;
import com.fss.domainobject.CarDriverMapperDO;
import com.fss.domainobject.DriverDO;
import com.fss.domainvalue.OnlineStatus;
import com.fss.exception.CarAlreadyInUseException;
import com.fss.exception.EntityNotFoundException;
import com.fss.service.car.CarService;
import com.fss.service.driver.DriverService;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Service;

@Service
public class CarDriverMapperServiceImpl implements CarDriverMapperService
{

    private final CarService carService;

    private final DriverService driverService;

    private final CarDriverMapperRepository carDriverMapperRepository;


    public CarDriverMapperServiceImpl(
        final CarService carService,
        final DriverService driverService,
        final CarDriverMapperRepository carDriverMapperRepository)
    {
        this.carService = carService;
        this.driverService = driverService;
        this.carDriverMapperRepository = carDriverMapperRepository;
    }


    @Override
    public void deselectCar(Long driverId)
    {
        CarDriverMapperDO carDriverMapperDO = carDriverMapperRepository.findByDriverId(driverId);

        if (null == carDriverMapperDO)
        {
            // Driver hasn't selected any car to deselect
            return;
        }

        // Deselecting car car driver
        carDriverMapperDO.setActive(false);
        carDriverMapperDO.setDateUpdated(ZonedDateTime.now());

        carDriverMapperRepository.save(carDriverMapperDO);

    }


    @Override
    public CarDriverMapperDO findByCar(Long carId)
    {
       return carDriverMapperRepository.findByCar(carId);
    }


    @Override
    public void selectCar(final Long driverId, final Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        // validating car and Driver exist
        CarDO carDO = carService.find(carId);
        DriverDO driverDO = driverService.find(driverId);

        if (OnlineStatus.ONLINE != driverDO.getOnlineStatus())
        {
            // Since the car should be selected by the driver who is online
            return;
        }

        CarDriverMapperDO existingCarDriverMapperDO = carDriverMapperRepository.findByCar(carId);
        if (null != existingCarDriverMapperDO)
        {
            if (existingCarDriverMapperDO.getDriverId() == driverId)
            {
                // Driver is already selected the given car
                return;
            }
            else
            {
                // If the car is in use by another driver
                throw new CarAlreadyInUseException("Car is already in use");
            }
        }

        // If the car is available and driver is also online, deselect the driver from any selected car
        this.deselectCar(driverId);

        // select given car with for given driver
        CarDriverMapperDO newCarDriverMapperDO = new CarDriverMapperDO();
        newCarDriverMapperDO.setCarId(carId);
        newCarDriverMapperDO.setDriverId(driverId);
        newCarDriverMapperDO.setActive(true);
        carDriverMapperRepository.save(newCarDriverMapperDO);
        carDriverMapperRepository.findByDriverId(driverId);

    }

}
