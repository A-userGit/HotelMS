package com.hotelms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullHotelDto extends CreateHotelDto{
    private List<String> amenities;
    private long id;

}
