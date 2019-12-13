package com.fss.dataaccessobject;

import com.fss.domainobject.CarDO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends CrudRepository<CarDO, Long>
{
    @Query("SELECT c FROM CarDO c WHERE c.deleted = false")
    List<CarDO> findAll();

    @Query("SELECT c FROM CarDO c WHERE c.id = :carId AND c.deleted = false ")
    Optional<CarDO> findById(@Param("carId") Long carId);

}
