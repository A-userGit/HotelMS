package com.hotelms.repository;

import com.hotelms.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {

    @Query("from amenities as a where a.name in :names")
    List<Amenity> findAllByNames(List<String> names);
}
