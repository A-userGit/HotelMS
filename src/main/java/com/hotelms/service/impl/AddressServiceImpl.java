package com.hotelms.service.impl;

import com.hotelms.dto.AddressDto;
import com.hotelms.entity.Address;
import com.hotelms.entity.Hotel;
import com.hotelms.mapper.AddressMapper;
import com.hotelms.repository.AddressRepository;
import com.hotelms.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public Address addAddress(AddressDto addressDto, Hotel hotel) {
        Address address = addressMapper.toAddress(addressDto);
        address.setHotel(hotel);
        return addressRepository.save(address);
    }
}
