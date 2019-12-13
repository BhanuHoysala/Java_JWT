package com.fss.dataaccessobject;

import com.fss.domainobject.CarDriverMapperDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CarDriverMapperRepository extends CrudRepository<CarDriverMapperDO, Long>
{

    @Query("SELECT cdm FROM CarDriverMapperDO cdm WHERE cdm.carId = :carId AND cdm.active = true")
    CarDriverMapperDO findByCar(@Param("carId") Long carId);

    @Query("SELECT cdm FROM CarDriverMapperDO cdm WHERE cdm.driverId = :driverId AND cdm.active = true")
    CarDriverMapperDO findByDriverId(Long driverId);

}
