package com.travelbooking.config;

import com.travelbooking.model.Flight;
import com.travelbooking.model.Hotel;
import com.travelbooking.model.Transportation;
import com.travelbooking.service.FlightService;
import com.travelbooking.service.HotelService;
import com.travelbooking.service.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private TransportationService transportationService;
    
    @Override
    public void run(String... args) throws Exception {
        initializeFlights();
        initializeHotels();
        initializeTransportation();
    }
    
    private void initializeFlights() {
        flightService.saveFlight(new Flight(null, "AA101", "American Airlines", "New York", "Los Angeles", 
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(6), 350.0, 50, "Economy"));
        
        flightService.saveFlight(new Flight(null, "UA202", "United Airlines", "New York", "Los Angeles", 
            LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(6), 420.0, 35, "Business"));
        
        flightService.saveFlight(new Flight(null, "DL303", "Delta Airlines", "Chicago", "Miami", 
            LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(3), 280.0, 60, "Economy"));
        
        flightService.saveFlight(new Flight(null, "SW404", "Southwest Airlines", "San Francisco", "Seattle", 
            LocalDateTime.now().plusDays(1).plusHours(8), LocalDateTime.now().plusDays(1).plusHours(10), 150.0, 80, "Economy"));
        
        flightService.saveFlight(new Flight(null, "BA505", "British Airways", "London", "Paris", 
            LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(2), 180.0, 45, "Business"));
        
        flightService.saveFlight(new Flight(null, "AF606", "Air France", "Paris", "Rome", 
            LocalDateTime.now().plusDays(4), LocalDateTime.now().plusDays(4).plusHours(2), 220.0, 55, "Economy"));
        
        flightService.saveFlight(new Flight(null, "EK707", "Emirates", "Dubai", "Singapore", 
            LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(6).plusHours(7), 850.0, 30, "First Class"));
        
        flightService.saveFlight(new Flight(null, "QR808", "Qatar Airways", "Doha", "Tokyo", 
            LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(7).plusHours(9), 920.0, 25, "Business"));
    }
    
    private void initializeHotels() {
        hotelService.saveHotel(new Hotel(null, "Grand Plaza Hotel", "New York", "123 Fifth Avenue, New York, NY", 
            5, 350.0, 20, "Pool, Spa, Gym, Restaurant, WiFi", "Deluxe Suite"));
        
        hotelService.saveHotel(new Hotel(null, "Sunset Beach Resort", "Los Angeles", "456 Ocean Blvd, Los Angeles, CA", 
            4, 280.0, 35, "Beach Access, Pool, WiFi, Parking", "Ocean View Room"));
        
        hotelService.saveHotel(new Hotel(null, "Downtown Business Hotel", "Chicago", "789 Michigan Ave, Chicago, IL", 
            4, 220.0, 40, "Business Center, Gym, WiFi, Meeting Rooms", "Standard Room"));
        
        hotelService.saveHotel(new Hotel(null, "Tropical Paradise Inn", "Miami", "321 Beach Drive, Miami, FL", 
            5, 400.0, 15, "Private Beach, Pool, Spa, Restaurant, Bar", "Premium Suite"));
        
        hotelService.saveHotel(new Hotel(null, "City Center Lodge", "San Francisco", "555 Market St, San Francisco, CA", 
            3, 180.0, 50, "WiFi, Breakfast, Parking", "Standard Room"));
        
        hotelService.saveHotel(new Hotel(null, "Eiffel Tower View Hotel", "Paris", "10 Rue de la Paix, Paris", 
            5, 450.0, 12, "City View, Restaurant, Concierge, WiFi", "Luxury Suite"));
        
        hotelService.saveHotel(new Hotel(null, "Roman Holiday Hotel", "Rome", "Via Veneto 25, Rome", 
            4, 320.0, 25, "Rooftop Terrace, Restaurant, WiFi", "Classic Room"));
        
        hotelService.saveHotel(new Hotel(null, "Marina Bay Suites", "Singapore", "8 Marina Boulevard, Singapore", 
            5, 480.0, 18, "Infinity Pool, Spa, Multiple Restaurants, Casino", "Sky Suite"));
    }
    
    private void initializeTransportation() {
        transportationService.saveTransportation(new Transportation(null, "Car Rental", "Enterprise", "New York", 
            65.0, 15, "Toyota Camry", 5, "GPS, Bluetooth, Auto"));
        
        transportationService.saveTransportation(new Transportation(null, "Car Rental", "Hertz", "Los Angeles", 
            70.0, 20, "Honda Accord", 5, "GPS, Backup Camera, Auto"));
        
        transportationService.saveTransportation(new Transportation(null, "SUV Rental", "Budget", "Chicago", 
            85.0, 10, "Ford Explorer", 7, "4WD, GPS, Spacious"));
        
        transportationService.saveTransportation(new Transportation(null, "Luxury Car", "Avis", "Miami", 
            150.0, 5, "BMW 5 Series", 5, "Leather Seats, Premium Sound, GPS"));
        
        transportationService.saveTransportation(new Transportation(null, "Van Rental", "Enterprise", "San Francisco", 
            95.0, 8, "Mercedes Sprinter", 12, "WiFi, Comfortable Seating"));
        
        transportationService.saveTransportation(new Transportation(null, "Car Rental", "Europcar", "Paris", 
            60.0, 12, "Peugeot 308", 5, "GPS, Auto, Compact"));
        
        transportationService.saveTransportation(new Transportation(null, "Scooter Rental", "Vespa Rentals", "Rome", 
            35.0, 25, "Vespa 150", 2, "Helmet Included, Easy Parking"));
        
        transportationService.saveTransportation(new Transportation(null, "Airport Shuttle", "SuperShuttle", "New York", 
            25.0, 50, "Mini Van", 8, "Door-to-Door Service, Luggage Space"));
    }
}
