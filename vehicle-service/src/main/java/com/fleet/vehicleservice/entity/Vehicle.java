package com.fleet.vehicleservice.entity;

import com.fleet.vehicleservice.enums.FuelType;
import com.fleet.vehicleservice.enums.VehicleStatus;
import com.fleet.vehicleservice.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String vehicleName;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private String brand;

    private String model;

    private Integer manufactureYear;

    private String color;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    private String insuranceNumber;

    private LocalDate insuranceExpiry;

    private LocalDate pollutionExpiry;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null){
            status = VehicleStatus.AVAILABLE;
        }
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
