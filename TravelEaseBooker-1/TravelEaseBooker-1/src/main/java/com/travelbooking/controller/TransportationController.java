package com.travelbooking.controller;

import com.travelbooking.model.Transportation;
import com.travelbooking.service.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportation")
@CrossOrigin(origins = "*")
public class TransportationController {
    
    @Autowired
    private TransportationService transportationService;
    
    @GetMapping
    public ResponseEntity<List<Transportation>> getAllTransportation() {
        return ResponseEntity.ok(transportationService.getAllTransportation());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transportation> getTransportationById(@PathVariable Long id) {
        return transportationService.getTransportationById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Transportation>> searchTransportation(
            @RequestParam String location,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(transportationService.searchTransportation(location, type));
    }
    
    @GetMapping("/search/type")
    public ResponseEntity<List<Transportation>> searchByType(@RequestParam String type) {
        return ResponseEntity.ok(transportationService.searchByType(type));
    }
}
