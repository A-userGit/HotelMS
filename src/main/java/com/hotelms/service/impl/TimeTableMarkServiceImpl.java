package com.hotelms.service.impl;

import com.hotelms.dto.ArrivalTimeDto;
import com.hotelms.entity.Hotel;
import com.hotelms.entity.TimeTableMark;
import com.hotelms.mapper.TimeTableMarkMapper;
import com.hotelms.repository.TimeTableMarkRepository;
import com.hotelms.service.TimeTableMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableMarkServiceImpl implements TimeTableMarkService {

    private final TimeTableMarkRepository timeTableMarkRepository;
    private final TimeTableMarkMapper timeTableMarkMapper;

    @Override
    @Transactional
    public List<TimeTableMark> addArrivalTimeToHotel(ArrivalTimeDto arrivalTimeDto, Hotel hotel) {
        List<TimeTableMark> timeTableMarksData = timeTableMarkMapper.toTimeTableMarks(arrivalTimeDto);
        for (TimeTableMark timeTableMark : timeTableMarksData) {
            timeTableMark.setHotel(hotel);
        }
        return timeTableMarkRepository.saveAll(timeTableMarksData);
    }
}
