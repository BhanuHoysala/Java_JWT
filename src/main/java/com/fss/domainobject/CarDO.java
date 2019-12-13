package com.fss.domainobject;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "UC_license_plate", columnNames = {"licensePlate"}))
public class CarDO
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "license Plate can not be null")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Seat Count Plate can not be null")
    private Integer seatCount;

    @Column
    private boolean convertible;

    @Column
    private Integer rating;

    @Column
    private String engineType;

    @Column
    private boolean deleted;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated = ZonedDateTime.now();
}
