package com.fss.dataaccessobject;

import com.fss.domainobject.CarDO;
import java.util.List;
import java.util.Map;

public interface CarRepositoryCustom
{
    List<CarDO> findCars(Map<String,String> searchParameters);
}
