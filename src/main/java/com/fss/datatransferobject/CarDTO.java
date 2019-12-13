package com.fss.datatransferobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDTO
{

    Long id;
    String licensePlate;
    Integer seatCount;
    boolean convertible = false;
    Integer rating;
    String engineType;
}
