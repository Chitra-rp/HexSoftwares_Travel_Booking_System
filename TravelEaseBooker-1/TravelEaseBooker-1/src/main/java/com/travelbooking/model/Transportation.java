package com.travelbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;
    private String provider;
    private String location;
    private Double pricePerDay;
    private Integer availableUnits;
    private String vehicleModel;
    private Integer passengerCapacity;
    private String features;
}
