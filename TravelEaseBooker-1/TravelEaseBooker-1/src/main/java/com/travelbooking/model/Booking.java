package com.travelbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bookingReference;
    private String serviceType;
    private Long serviceId;
    private String customerName;
    private String customerEmail;
    private LocalDateTime bookingDate;
    private String status;
    private Double totalPrice;
    private String bookingDetails;
}
