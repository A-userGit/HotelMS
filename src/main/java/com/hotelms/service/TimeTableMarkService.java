package com.hotelms.service;

import com.hotelms.dto.ArrivalTimeDto;
import com.hotelms.entity.Hotel;
import com.hotelms.entity.TimeTableMark;

import java.util.List;

public interface TimeTableMarkService {
    List<TimeTableMark> addArrivalTimeToHotel(ArrivalTimeDto arrivalTimeDto, Hotel hotel);
}
