package com.travelbooking.controller;

import com.travelbooking.model.Hotel;
import com.travelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {
    
    @Autowired
    private HotelService hotelService;
    
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam String location,
            @RequestParam(required = false) Integer minStars) {
        return ResponseEntity.ok(hotelService.searchHotels(location, minStars));
    }
    
    @GetMapping("/search/price")
    public ResponseEntity<List<Hotel>> searchByPrice(@RequestParam Double maxPrice) {
        return ResponseEntity.ok(hotelService.searchHotelsByPrice(maxPrice));
    }
}
