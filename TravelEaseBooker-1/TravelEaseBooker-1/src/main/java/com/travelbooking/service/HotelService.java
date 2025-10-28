package com.travelbooking.service;

import com.travelbooking.exception.InsufficientCapacityException;
import com.travelbooking.exception.ResourceNotFoundException;
import com.travelbooking.model.Hotel;
import com.travelbooking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    
    @Autowired
    private HotelRepository hotelRepository;
    
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    
    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }
    
    public List<Hotel> searchHotels(String location, Integer minStars) {
        if (minStars != null) {
            return hotelRepository.findByLocationAndStarRatingGreaterThanEqual(location, minStars);
        }
        return hotelRepository.findByLocation(location);
    }
    
    public List<Hotel> searchHotelsByPrice(Double maxPrice) {
        return hotelRepository.findByPricePerNightLessThanEqual(maxPrice);
    }
    
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
    
    public boolean hasAvailableRooms(Long hotelId, Integer requiredRooms) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        if (hotelOpt.isEmpty()) {
            return false;
        }
        return hotelOpt.get().getAvailableRooms() >= requiredRooms;
    }
    
    public void updateAvailableRooms(Long hotelId, Integer bookedRooms) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        
        int newAvailability = hotel.getAvailableRooms() - bookedRooms;
        if (newAvailability < 0) {
            throw new InsufficientCapacityException("Insufficient rooms available at hotel: " + hotel.getName());
        }
        
        hotel.setAvailableRooms(newAvailability);
        hotelRepository.save(hotel);
    }
}
