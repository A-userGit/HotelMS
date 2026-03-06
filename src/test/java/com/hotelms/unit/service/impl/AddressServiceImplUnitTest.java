package com.hotelms.unit.service.impl;

import com.hotelms.dto.AddressDto;
import com.hotelms.entity.Address;
import com.hotelms.entity.Hotel;
import com.hotelms.mapper.AddressMapper;
import com.hotelms.repository.AddressRepository;
import com.hotelms.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for address service")
@ExtendWith(MockitoExtension.class)
public class AddressServiceImplUnitTest {

  @Mock private AddressRepository addressRepository;
  @Mock private AddressMapper addressMapper;
  @InjectMocks private AddressServiceImpl addressService;

  @Test
  @DisplayName("Test adding address")
  public void testAddAddress() {
    Hotel hotel = new Hotel();
    AddressDto addressDto = new AddressDto();
    addressDto.setCity("city");
    addressDto.setCountry("country");
    addressDto.setStreet("street");
    addressDto.setHouseNumber(12);
    addressDto.setPostCode("123456");
    Address address = new Address();
    when(addressMapper.toAddress(addressDto)).thenReturn(address);
    addressService.addAddress(addressDto, hotel);
    verify(addressMapper, times(1)).toAddress(addressDto);
    verify(addressRepository, times(1)).save(address);
  }
}
