package com.travelbooking.service;

import com.travelbooking.exception.InsufficientCapacityException;
import com.travelbooking.exception.ResourceNotFoundException;
import com.travelbooking.model.Transportation;
import com.travelbooking.repository.TransportationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportationService {
    
    @Autowired
    private TransportationRepository transportationRepository;
    
    public List<Transportation> getAllTransportation() {
        return transportationRepository.findAll();
    }
    
    public Optional<Transportation> getTransportationById(Long id) {
        return transportationRepository.findById(id);
    }
    
    public List<Transportation> searchTransportation(String location, String type) {
        if (type != null && !type.isEmpty()) {
            return transportationRepository.findByLocationAndType(location, type);
        }
        return transportationRepository.findByLocation(location);
    }
    
    public List<Transportation> searchByType(String type) {
        return transportationRepository.findByType(type);
    }
    
    public Transportation saveTransportation(Transportation transportation) {
        return transportationRepository.save(transportation);
    }
    
    public boolean hasAvailableUnits(Long transportationId, Integer requiredUnits) {
        Optional<Transportation> transportOpt = transportationRepository.findById(transportationId);
        if (transportOpt.isEmpty()) {
            return false;
        }
        return transportOpt.get().getAvailableUnits() >= requiredUnits;
    }
    
    public void updateAvailableUnits(Long transportationId, Integer bookedUnits) {
        Transportation transportation = transportationRepository.findById(transportationId)
            .orElseThrow(() -> new ResourceNotFoundException("Transportation not found with id: " + transportationId));
        
        int newAvailability = transportation.getAvailableUnits() - bookedUnits;
        if (newAvailability < 0) {
            throw new InsufficientCapacityException("Insufficient units available for transportation: " + transportation.getVehicleModel());
        }
        
        transportation.setAvailableUnits(newAvailability);
        transportationRepository.save(transportation);
    }
}
