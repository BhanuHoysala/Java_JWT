package com.fss.datatransferobject;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO to perform car selection by a Driver
 */
@Data
public class CarDriverMapperDTO
{
    @NotNull(message = "Driver Id cannot not be null!")
    private final Long driverId;

    @NotNull(message = "Car Id cannot not be null!")
    private final Long carId;


    public CarDriverMapperDTO(Long driverId, Long carId)
    {
        this.driverId = driverId;
        this.carId = carId;
    }

}
