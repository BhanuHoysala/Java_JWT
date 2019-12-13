package com.fss.service.cardrivermapper;

import com.fss.domainobject.CarDriverMapperDO;
import com.fss.exception.CarAlreadyInUseException;
import com.fss.exception.EntityNotFoundException;

public interface CarDriverMapperService
{
    /**
     *
     * @param driverId
     * @param carId
     * @throws EntityNotFoundException
     * @throws CarAlreadyInUseException
     */
    void selectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;

    /**
     *
     * @param driverId
     */
    void deselectCar(Long driverId);

    /**
     *
     * @param carId
     * @return
     */
    CarDriverMapperDO findByCar(Long carId);
}
