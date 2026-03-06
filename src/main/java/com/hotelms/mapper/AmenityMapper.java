package com.hotelms.mapper;

import com.hotelms.entity.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

    @Named("stringifyAmenities")
    default List<String> stringify(List<Amenity> amenities) {
        return amenities.stream().map(Amenity::getName).toList();
    }

    default List<Amenity> toAmenity(List<String> amenities) {
        List<Amenity> amenityList = new ArrayList<>();
        amenities.forEach(amenityName -> {
            Amenity amenity = new Amenity();
            amenity.setName(amenityName);
            amenityList.add(amenity);
        });
        return amenityList;
    }

}
