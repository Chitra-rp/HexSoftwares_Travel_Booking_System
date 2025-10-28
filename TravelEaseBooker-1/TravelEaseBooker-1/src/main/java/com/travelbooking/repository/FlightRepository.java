package com.travelbooking.repository;

import com.travelbooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestination(String origin, String destination);
    List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(
        String origin, String destination, LocalDateTime start, LocalDateTime end);
    List<Flight> findByPriceLessThanEqual(Double maxPrice);
}
