package com.hotelms.service;

import com.hotelms.dto.HotelContactsDto;
import com.hotelms.entity.Contact;
import com.hotelms.entity.Hotel;
import com.hotelms.enums.ContactType;

import java.util.List;

public interface ContactService {
  HotelContactsDto getHotelContacts(long hotelId);

  List<Contact> addHotelContacts(HotelContactsDto hotelContactsDto, Hotel hotel);

  List<Contact> getHotelContactByType(long hotelId, List<ContactType> contactTypes);
}
