package com.hotelms.service;

import com.hotelms.dto.HotelContactsDto;
import com.hotelms.entity.Contact;
import com.hotelms.entity.Hotel;

import java.util.List;

public interface ContactService {

  List<Contact> addHotelContacts(HotelContactsDto hotelContactsDto, Hotel hotel);
}
