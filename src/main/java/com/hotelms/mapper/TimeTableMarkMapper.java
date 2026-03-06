package com.hotelms.mapper;

import com.hotelms.dto.ArrivalTimeDto;
import com.hotelms.entity.TimeTableMark;
import com.hotelms.enums.TimeMarkTypes;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeTableMarkMapper {

  @Named("getArrivalTime")
  default ArrivalTimeDto toArrivalTimeDto(List<TimeTableMark> timeTableMarks) {
    ArrivalTimeDto arrivalTimeDto = new ArrivalTimeDto();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    timeTableMarks.forEach(
        mark -> {
          if (mark.getType() == TimeMarkTypes.CHECK_IN) {
            arrivalTimeDto.setCheckIn(mark.getTimeMark().toLocalTime().format(formatter));
          }
          if (mark.getType() == TimeMarkTypes.CHECK_OUT) {
            arrivalTimeDto.setCheckOut(mark.getTimeMark().toLocalTime().format(formatter));
          }
        });
    return arrivalTimeDto;
  }

  default List<TimeTableMark> toTimeTableMarks(ArrivalTimeDto arrivalTimeDto) {
    List<TimeTableMark> timeTableMarks = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    if (arrivalTimeDto.getCheckIn() != null && !arrivalTimeDto.getCheckIn().isEmpty()) {
      TimeTableMark checkInMark = new TimeTableMark();
      LocalTime localTimeCheckIn = LocalTime.parse(arrivalTimeDto.getCheckIn(), formatter);
      checkInMark.setTimeMark(Time.valueOf(localTimeCheckIn));
      checkInMark.setRequired(true);
      checkInMark.setType(TimeMarkTypes.CHECK_IN);
      timeTableMarks.add(checkInMark);
    }
    if (arrivalTimeDto.getCheckOut() != null && !arrivalTimeDto.getCheckOut().isEmpty()) {
      TimeTableMark checkOutMark = new TimeTableMark();
      LocalTime localTimeCheckOut = LocalTime.parse(arrivalTimeDto.getCheckOut(), formatter);
      checkOutMark.setTimeMark(Time.valueOf(localTimeCheckOut));
      checkOutMark.setRequired(false);
      checkOutMark.setType(TimeMarkTypes.CHECK_OUT);
      timeTableMarks.add(checkOutMark);
    }
    return timeTableMarks;
  }
}
