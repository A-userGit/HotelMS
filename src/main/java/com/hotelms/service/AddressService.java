package com.hotelms.service;

import com.hotelms.dto.AddressDto;
import com.hotelms.entity.Address;
import com.hotelms.entity.Hotel;

public interface AddressService {
    Address addAddress(AddressDto addressDto, Hotel hotel);
}
