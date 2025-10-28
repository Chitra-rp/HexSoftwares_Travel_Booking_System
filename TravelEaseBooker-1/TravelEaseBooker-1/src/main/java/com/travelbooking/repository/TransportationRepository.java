package com.travelbooking.repository;

import com.travelbooking.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    List<Transportation> findByLocation(String location);
    List<Transportation> findByType(String type);
    List<Transportation> findByLocationAndType(String location, String type);
}
