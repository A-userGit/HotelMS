package com.hotelms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotelDto {
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private HotelContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
}
