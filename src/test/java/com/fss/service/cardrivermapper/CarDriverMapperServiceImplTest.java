package com.fss.service.cardrivermapper;

import com.fss.FreeNowServerApplicantTestApplication;
import com.fss.domainobject.CarDO;
import com.fss.domainobject.CarDriverMapperDO;
import com.fss.domainobject.DriverDO;
import com.fss.domainvalue.OnlineStatus;
import com.fss.exception.CarAlreadyInUseException;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import com.fss.service.car.CarService;
import com.fss.service.driver.DriverService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FreeNowServerApplicantTestApplication.class)
public class CarDriverMapperServiceImplTest
{

    @Autowired
    private CarDriverMapperService carDriverMapperService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CarService carService;


    @Test
    @Description("Testing car selection by a Driver")
    public void selectCar() throws ConstraintsViolationException, CarAlreadyInUseException, EntityNotFoundException
    {

        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 0101");
        carService.create(carDO);

        DriverDO driverDO = new DriverDO("Driver11", "Password11");
        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        driverService.create(driverDO);

        carDriverMapperService.findByCar(carDO.getId());
        carDriverMapperService.selectCar(driverDO.getId(), carDO.getId());

        CarDriverMapperDO carDriverMap = carDriverMapperService.findByCar(carDO.getId());

        Assert.assertEquals("KA 05 JS 0101", carDriverMap.getCarDO().getLicensePlate());
    }


    @Test
    @Description("Testing car deselection by a Driver")
    public void deselectCar() throws ConstraintsViolationException, CarAlreadyInUseException, EntityNotFoundException
    {
        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 0202");
        carService.create(carDO);

        DriverDO driverDO = new DriverDO("Driver12", "Password12");
        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        driverService.create(driverDO);

        carDriverMapperService.findByCar(carDO.getId());
        carDriverMapperService.selectCar(driverDO.getId(), carDO.getId());

        CarDriverMapperDO carDriverMap = carDriverMapperService.findByCar(carDO.getId());

        Assert.assertEquals("KA 05 JS 0202", carDriverMap.getCarDO().getLicensePlate());
        Assert.assertEquals(true, carDriverMap.getActive());

        carDriverMapperService.deselectCar(driverDO.getId());

        // Since the deselect has done there shouldn't be any active record
        Assert.assertNull(carDriverMapperService.findByCar(carDO.getId()));

    }


    @Test(expected = CarAlreadyInUseException.class)
    @Description("Testing Car already in use Exception")
    public void testCarIsAlreadyInUseException() throws CarAlreadyInUseException,
                                                        ConstraintsViolationException,
                                                        EntityNotFoundException
    {

        CarDO carDO = new CarDO();
        carDO.setSeatCount(4);
        carDO.setLicensePlate("KA 05 JS 0303");
        carService.create(carDO);

        DriverDO driverDO = new DriverDO("Driver13", "Password13");
        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        driverService.create(driverDO);

        carDriverMapperService.findByCar(carDO.getId());
        carDriverMapperService.selectCar(driverDO.getId(), carDO.getId());

        CarDriverMapperDO carDriverMap = carDriverMapperService.findByCar(carDO.getId());

        Assert.assertEquals("KA 05 JS 0303", carDriverMap.getCarDO().getLicensePlate());
        Assert.assertEquals(true, carDriverMap.getActive());

        DriverDO driverToSelectCarInUse = new DriverDO("Driver14", "Password14");
        driverToSelectCarInUse.setOnlineStatus(OnlineStatus.ONLINE);
        driverService.create(driverToSelectCarInUse);

        carDriverMapperService.selectCar(driverToSelectCarInUse.getId(), carDO.getId());
    }

}