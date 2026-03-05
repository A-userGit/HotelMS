package com.hotelms.service;

import com.hotelms.dto.*;
import com.hotelms.enums.HistogramType;

import java.util.HashMap;
import java.util.List;

public interface HotelService {
    HotelShortDto addHotel(CreateHotelDto createHotelDto);

    List<HotelShortDto> getAll();

    FullHotelDto getById(Long aLong);

    List<HotelShortDto> getByParameters(SearchParameters parameters);

    HashMap<String, Long> getHistogram(HistogramType histogramType);

    void setAmenities(List<String> amenitiesNames, long hotelId);
}
