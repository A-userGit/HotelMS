package com.hotelms.mapper;

import com.hotelms.dto.CreateHotelDto;
import com.hotelms.dto.FullHotelDto;
import com.hotelms.dto.HotelShortDBDto;
import com.hotelms.dto.HotelShortDto;
import com.hotelms.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
      AddressMapper.class,
      ContactMapper.class,
      TimeTableMarkMapper.class,
      AmenityMapper.class
    })
public interface HotelMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "address", ignore = true)
  @Mapping(target = "contacts", ignore = true)
  @Mapping(target = "amenities", ignore = true)
  @Mapping(target = "timeTableMarks", ignore = true)
  Hotel toHotel(CreateHotelDto createHotelDto);

  @Mapping(target = "phone", source = "contacts", qualifiedByName = "phoneFromContacts")
  @Mapping(target = "address", source = "address", qualifiedByName = "addressStringified")
  HotelShortDto toHotelShortDto(Hotel hotel);

  @Mapping(target = "address", source = "address", qualifiedByName = "addressStringified")
  HotelShortDto toHotelShortDto(HotelShortDBDto hotelShortDBDto);

  @Mapping(target = "arrivalTime", source = "timeTableMarks", qualifiedByName = "getArrivalTime")
  @Mapping(target = "amenities", source = "amenities", qualifiedByName = "stringifyAmenities")
  FullHotelDto toFullHotelDto(Hotel hotel);
}
