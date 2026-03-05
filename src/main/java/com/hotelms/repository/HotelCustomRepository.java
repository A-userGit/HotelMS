package com.hotelms.repository;

import com.hotelms.dto.HistogramDto;
import com.hotelms.dto.HotelShortDBDto;
import com.hotelms.dto.SearchParameters;
import com.hotelms.enums.HistogramType;

import java.util.List;

public interface HotelCustomRepository {
    List<HotelShortDBDto> findAllHotelsByParameters(SearchParameters searchParameters);
    List<HistogramDto> findAllHistogramsByParameter(HistogramType parameter);
}
