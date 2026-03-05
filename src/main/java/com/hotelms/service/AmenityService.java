package com.hotelms.service;

import com.hotelms.entity.Amenity;

import java.util.List;

public interface AmenityService {
    public List<Amenity> addAmenities(List<String> amenityNames);

}
