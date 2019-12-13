package com.fss.service.car;

import com.fss.FreeNowServerApplicantTestApplication;
import com.fss.domainobject.CarDO;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FreeNowServerApplicantTestApplication.class)
public class CarServiceImplTest
{

    @Autowired
    private CarService carService;


    @Test
    @Description("Creating a new car")
    public void createCar() throws ConstraintsViolationException
    {
        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 4748");
        carDO.setRating(4);
        carDO.setConvertible(false);
        carDO.setEngineType("gas");

        carService.create(carDO);
        Assert.assertNotNull(carDO.getId());
    }


    @Test(expected = ConstraintsViolationException.class)
    @Description("Creating a new car with existing License plate")
    public void createCarWithExistingLicense() throws ConstraintsViolationException
    {

        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 4749");
        carService.create(carDO);

        CarDO constraintsViolationCarDO = new CarDO();
        constraintsViolationCarDO.setSeatCount(6);
        constraintsViolationCarDO.setLicensePlate("KA 05 JS 4749");
        carService.create(constraintsViolationCarDO);
    }


    @Test(expected = EntityNotFoundException.class)
    @Description("Testing searching for car by car Id working")
    public void find() throws EntityNotFoundException
    {

        Long id = carService.findAll().stream().max(Comparator.comparing(CarDO::getId)).get().getId();
        // Adding 10 to make sure even if any other units add new car record into car table
        Assert.assertNotNull(carService.find(id + 10));
    }


    @Test
    @Description("Testing searching for car by car Id which doesn't exist")
    public void findNonExistingCar() throws ConstraintsViolationException, EntityNotFoundException
    {

        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 9999");
        carService.create(carDO);

        Assert.assertNotNull(carService.find(carDO.getId()));
    }


    @Test(expected = EntityNotFoundException.class)
    @Description("Deleting a car from car table")
    public void delete() throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 8888");
        carService.create(carDO);

        Assert.assertNotNull(carDO.getId());

        carService.delete(carDO.getId());
        carService.find(carDO.getId());
    }


    @Test
    @Description("Testing find all cars method")
    public void findAll() throws ConstraintsViolationException
    {
        CarDO car1 = new CarDO();
        car1.setSeatCount(4);
        car1.setLicensePlate("KA 05 JS 7777");
        carService.create(car1);

        CarDO car2 = new CarDO();
        car2.setSeatCount(4);
        car2.setLicensePlate("KA 05 JS 6666");
        carService.create(car2);

        List<CarDO> allCars = carService.findAll();
        Assert.assertFalse(allCars.isEmpty());

        Assert.assertTrue(allCars.stream().anyMatch(car -> car.getLicensePlate().equals("KA 05 JS 6666")));


    }
}