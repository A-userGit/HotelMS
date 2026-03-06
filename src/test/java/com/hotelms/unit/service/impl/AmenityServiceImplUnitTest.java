package com.hotelms.unit.service.impl;

import com.hotelms.entity.Amenity;
import com.hotelms.mapper.AmenityMapper;
import com.hotelms.repository.AmenityRepository;
import com.hotelms.service.impl.AmenityServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@DisplayName("Tests for hotel service")
@ExtendWith(MockitoExtension.class)
public class AmenityServiceImplUnitTest {

  @Mock private AmenityRepository amenityRepository;
  @Mock private AmenityMapper amenityMapper;

  @InjectMocks private AmenityServiceImpl amenityService;

  @Test
  @DisplayName("Try set amenities")
  public void addAmenitiesTest() {
    List<String> amenitiesNames = new ArrayList<>();
    amenitiesNames.add("Amenity 1");
    amenitiesNames.add("Amenity 2");
    Amenity oldAmenity = new Amenity();
    oldAmenity.setName("Amenity 1");
    Amenity newAmenity = new Amenity();
    newAmenity.setName("Amenity 2");
    List<Amenity> prev = new ArrayList<>();
    prev.add(oldAmenity);
    List<Amenity> newAmenities = new ArrayList<>();
    newAmenities.add(newAmenity);
    when(amenityRepository.findAllByNames(amenitiesNames)).thenReturn(prev);
    amenitiesNames.remove(oldAmenity.getName());
    when(amenityMapper.toAmenity(amenitiesNames)).thenReturn(newAmenities);
    when(amenityRepository.saveAll(newAmenities)).thenReturn(newAmenities);
    try {
      amenityService.addAmenities(amenitiesNames);
    } catch (Exception e) {
      fail("Test failed incorrect amount of amenities" + e.getLocalizedMessage());
    }
  }
}
