package com.fss.service.driver;

import com.fss.domainobject.DriverDO;
import com.fss.domainvalue.OnlineStatus;
import com.fss.exception.ConstraintsViolationException;
import com.fss.exception.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DriverService
{

    /**
     *
     * @param driverId
     * @return
     * @throws EntityNotFoundException
     */
    DriverDO find(Long driverId) throws EntityNotFoundException;

    /**
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException
     */
    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    /**
     *
     * @param driverId
     * @throws EntityNotFoundException
     */
    void delete(Long driverId) throws EntityNotFoundException;

    /**
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    /**
     *
     * @param onlineStatus
     * @return
     */
    List<DriverDO> find(OnlineStatus onlineStatus);

    /**
     *
     * @param entrySet
     * @return
     */
    List<DriverDO> search(Set<Map.Entry<String, String>> entrySet);

    /**
     *
     * @param username
     * @return
     */
    DriverDO findUserByName(String username);

    /**
     *
     * @param onlineStatus
     */
    void updateOnlineStatus(Long driverId, OnlineStatus onlineStatus) throws EntityNotFoundException;
}
