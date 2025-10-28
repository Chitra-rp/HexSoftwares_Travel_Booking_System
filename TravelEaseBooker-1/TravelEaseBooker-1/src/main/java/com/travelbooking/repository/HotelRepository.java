package com.travelbooking.repository;

import com.travelbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocation(String location);
    List<Hotel> findByLocationAndStarRatingGreaterThanEqual(String location, Integer starRating);
    List<Hotel> findByPricePerNightLessThanEqual(Double maxPrice);
}
