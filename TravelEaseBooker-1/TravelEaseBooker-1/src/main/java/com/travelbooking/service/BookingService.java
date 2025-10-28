package com.travelbooking.service;

import com.travelbooking.exception.BookingException;
import com.travelbooking.exception.InsufficientCapacityException;
import com.travelbooking.exception.ResourceNotFoundException;
import com.travelbooking.model.Booking;
import com.travelbooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private TransportationService transportationService;
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    
    public Optional<Booking> getBookingByReference(String reference) {
        return bookingRepository.findByBookingReference(reference);
    }
    
    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByCustomerEmail(email);
    }
    
    public Booking createBooking(Booking booking) {
        validateBooking(booking);
        
        booking.setBookingReference(generateBookingReference());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        
        updateAvailability(booking.getServiceType(), booking.getServiceId());
        
        return bookingRepository.save(booking);
    }
    
    private void validateBooking(Booking booking) {
        String serviceType = booking.getServiceType().toUpperCase();
        Long serviceId = booking.getServiceId();
        
        switch (serviceType) {
            case "FLIGHT":
                var flight = flightService.getFlightById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + serviceId));
                if (flight.getAvailableSeats() < 1) {
                    throw new InsufficientCapacityException("No seats available for flight: " + flight.getFlightNumber());
                }
                break;
            case "HOTEL":
                var hotel = hotelService.getHotelById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + serviceId));
                if (hotel.getAvailableRooms() < 1) {
                    throw new InsufficientCapacityException("No rooms available at hotel: " + hotel.getName());
                }
                break;
            case "TRANSPORTATION":
                var transportation = transportationService.getTransportationById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Transportation not found with id: " + serviceId));
                if (transportation.getAvailableUnits() < 1) {
                    throw new InsufficientCapacityException("No units available for transportation: " + transportation.getVehicleModel());
                }
                break;
            default:
                throw new BookingException("Invalid service type: " + serviceType);
        }
    }
    
    public void cancelBooking(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with reference: " + bookingReference));
        
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
    
    private String generateBookingReference() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private void updateAvailability(String serviceType, Long serviceId) {
        switch (serviceType.toUpperCase()) {
            case "FLIGHT":
                flightService.updateAvailableSeats(serviceId, 1);
                break;
            case "HOTEL":
                hotelService.updateAvailableRooms(serviceId, 1);
                break;
            case "TRANSPORTATION":
                transportationService.updateAvailableUnits(serviceId, 1);
                break;
        }
    }
}
