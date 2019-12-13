package com.fss.service.driver;

import com.fss.dataaccessobject.DriverRepository;
import com.fss.domainobject.DriverDO;
import com.fss.domainvalue.GeoCoordinate;
import com.fss.domainvalue.OnlineStatus;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import com.fss.service.car.CarService;
import com.fss.service.cardrivermapper.CarDriverMapperService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Slf4j
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    @Autowired
    private CarDriverMapperService carDriverMapperService;

    @Autowired
    private CarService carService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public DefaultDriverService(final DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;

        try
        {
            driverDO.setPassword(passwordEncoder.encode(driverDO.getPassword()));
            log.info("Password encrypted to - {}", passwordEncoder.encode(driverDO.getPassword()));
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    @Override
    public List<DriverDO> search(final Set<Map.Entry<String, String>> entrySet)
    {
        return null;
    }


    private DriverDO findDriverChecked(final Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    @Override
    public DriverDO findUserByName(final String username)
    {
        return driverRepository.findByUsername(username);
    }


    @Override
    public void updateOnlineStatus(Long driverId, OnlineStatus onlineStatus) throws EntityNotFoundException
    {
        DriverDO driver = findDriverChecked(driverId);
        driver.setOnlineStatus(onlineStatus);
        driverRepository.save(driver);
    }
}
