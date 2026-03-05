package com.hotelms.service.impl;

import com.hotelms.entity.Amenity;
import com.hotelms.mapper.AmenityMapper;
import com.hotelms.repository.AmenityRepository;
import com.hotelms.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

  private final AmenityRepository amenityRepository;
  private final AmenityMapper amenityMapper;

  @Override
  public List<Amenity> addAmenities(List<String> amenityNames) {
    List<Amenity> allByNames = amenityRepository.findAllByNames(amenityNames);
    List<String> newAmenitiesNames =
        amenityNames.stream()
            .filter(
                name -> allByNames.stream().noneMatch(amenity -> amenity.getName().equals(name)))
            .toList();
    List<Amenity> amenity = amenityMapper.toAmenity(newAmenitiesNames);
    List<Amenity> newAmenities = amenityRepository.saveAll(amenity);
    newAmenities.addAll(allByNames);
    return newAmenities;
  }
}
