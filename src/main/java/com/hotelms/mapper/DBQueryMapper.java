package com.hotelms.mapper;

import com.hotelms.dto.HistogramDto;
import com.hotelms.dto.HotelShortDBDto;
import com.hotelms.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DBQueryMapper {

  default HotelShortDBDto toShortDBHotel(Object[] data) {
    HotelShortDBDto hotelShortDBDto = new HotelShortDBDto();
    hotelShortDBDto.setId((Long) data[0]);
    hotelShortDBDto.setName((String) data[1]);
    hotelShortDBDto.setDescription((String) data[2]);
    hotelShortDBDto.setPhone((String) data[3]);
    hotelShortDBDto.setAddress((Address) data[4]);
    return hotelShortDBDto;
  }

  default HistogramDto toHistogramDto(Object[] data) {
    HistogramDto histogramDto = new HistogramDto();
    histogramDto.setName((String) data[0]);
    histogramDto.setValue((Long) data[1]);
    return histogramDto;
  }
}
