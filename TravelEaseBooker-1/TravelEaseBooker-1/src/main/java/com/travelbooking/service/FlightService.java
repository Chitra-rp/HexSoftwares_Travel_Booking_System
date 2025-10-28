package com.travelbooking.service;

import com.travelbooking.exception.InsufficientCapacityException;
import com.travelbooking.exception.ResourceNotFoundException;
import com.travelbooking.model.Flight;
import com.travelbooking.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    
    public List<Flight> searchFlights(String origin, String destination, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return flightRepository.findByOriginAndDestinationAndDepartureTimeBetween(
                origin, destination, startDate, endDate);
        }
        return flightRepository.findByOriginAndDestination(origin, destination);
    }
    
    public List<Flight> searchFlightsByPrice(Double maxPrice) {
        return flightRepository.findByPriceLessThanEqual(maxPrice);
    }
    
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }
    
    public boolean hasAvailableSeats(Long flightId, Integer requiredSeats) {
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        if (flightOpt.isEmpty()) {
            return false;
        }
        return flightOpt.get().getAvailableSeats() >= requiredSeats;
    }
    
    public void updateAvailableSeats(Long flightId, Integer bookedSeats) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + flightId));
        
        int newAvailability = flight.getAvailableSeats() - bookedSeats;
        if (newAvailability < 0) {
            throw new InsufficientCapacityException("Insufficient seats available for flight: " + flight.getFlightNumber());
        }
        
        flight.setAvailableSeats(newAvailability);
        flightRepository.save(flight);
    }
}
