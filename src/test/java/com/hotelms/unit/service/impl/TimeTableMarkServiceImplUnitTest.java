package com.hotelms.unit.service.impl;

import com.hotelms.dto.ArrivalTimeDto;
import com.hotelms.entity.Hotel;
import com.hotelms.entity.TimeTableMark;
import com.hotelms.mapper.TimeTableMarkMapper;
import com.hotelms.repository.TimeTableMarkRepository;
import com.hotelms.service.impl.TimeTableMarkServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for timetable marks service")
@ExtendWith(MockitoExtension.class)
public class TimeTableMarkServiceImplUnitTest {

  @Mock private TimeTableMarkRepository timeTableMarkRepository;
  @Mock private TimeTableMarkMapper timeTableMarkMapper;
  @InjectMocks private TimeTableMarkServiceImpl timeTableMarkService;

  @Test
  @DisplayName("Try set arrival time")
  public void addArrivalTimeToHotelTest() {
    ArrivalTimeDto arrivalTimeDto = new ArrivalTimeDto();
    arrivalTimeDto.setCheckIn("11:00");
    arrivalTimeDto.setCheckOut("12:00");
    List<TimeTableMark> timeTableMarkList = new ArrayList<>();
    TimeTableMark checkInMark = new TimeTableMark();
    TimeTableMark checkoutMark = new TimeTableMark();
    timeTableMarkList.add(checkInMark);
    timeTableMarkList.add(checkoutMark);
    when(timeTableMarkMapper.toTimeTableMarks(arrivalTimeDto)).thenReturn(timeTableMarkList);
    timeTableMarkService.addArrivalTimeToHotel(arrivalTimeDto, new Hotel());
    verify(timeTableMarkMapper, times(1)).toTimeTableMarks(arrivalTimeDto);
    verify(timeTableMarkRepository, times(1)).saveAll(timeTableMarkList);
  }
}
