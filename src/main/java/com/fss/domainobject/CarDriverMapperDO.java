package com.fss.domainobject;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * CarDriverMapperDO entity will also serves as a Car driver pair history
 */
@Data
@Entity
@Table(name = "car_driver_mapper")
public class CarDriverMapperDO
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long driverId;

    @Column(nullable = false)
    private Long carId;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated;

    @OneToOne
    @JoinColumn(name = "carId", insertable = false, updatable = false)
    private CarDO carDO;

    @OneToOne
    @JoinColumn(name = "driverId", insertable = false, updatable = false)
    private DriverDO driverDO;
}
