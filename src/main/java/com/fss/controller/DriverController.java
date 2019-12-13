package com.fss.controller;

import com.fss.controller.mapper.DriverMapper;
import com.fss.datatransferobject.CarDriverMapperDTO;
import com.fss.datatransferobject.DriverDTO;
import com.fss.domainobject.DriverDO;
import com.fss.domainvalue.OnlineStatus;
import com.fss.exception.CarAlreadyInUseException;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import com.fss.service.cardrivermapper.CarDriverMapperService;
import com.fss.service.driver.DriverService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;

    @Autowired
    CarDriverMapperService carDriverMapperService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @PutMapping("/updatestatus/{driverId}")
    public void updateOnlineStatus(@PathVariable Long driverId, @RequestParam OnlineStatus onlineStatus)
        throws EntityNotFoundException
    {
        driverService.updateOnlineStatus(driverId, onlineStatus);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @PutMapping("/deslectcar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deselectCar(@RequestParam Long driverId)
    {
        carDriverMapperService.deselectCar(driverId);
    }


    @PostMapping("/selectcar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void selectCar(@RequestBody CarDriverMapperDTO carDriverMapperDTO, HttpServletResponse response)
        throws EntityNotFoundException, CarAlreadyInUseException
    {
        carDriverMapperService.selectCar(carDriverMapperDTO.getDriverId(), carDriverMapperDTO.getCarId());
    }

}
