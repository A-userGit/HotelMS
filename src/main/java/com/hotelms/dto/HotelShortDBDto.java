package com.hotelms.dto;

import com.hotelms.entity.Address;
import com.hotelms.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelShortDBDto {
    private String phone;
    private String name;
    private Address address;
    private String description;
    private Long id;
}
