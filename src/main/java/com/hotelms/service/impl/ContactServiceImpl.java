package com.hotelms.service.impl;

import com.hotelms.dto.HotelContactsDto;
import com.hotelms.entity.Contact;
import com.hotelms.entity.Hotel;
import com.hotelms.enums.ContactType;
import com.hotelms.repository.ContactRepository;
import com.hotelms.service.ContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

  private final ContactRepository contactRepository;

  @Override
  @Transactional
  public List<Contact> addHotelContacts(HotelContactsDto hotelContactsDto, Hotel hotel) {
    List<Contact> contacts = new ArrayList<>();
    Contact phone = new Contact();
    phone.setType(ContactType.PHONE);
    phone.setHotel(hotel);
    phone.setContactValue(hotelContactsDto.getPhone());
    contactRepository.save(phone);
    contacts.add(phone);
    Contact email = new Contact();
    email.setType(ContactType.EMAIL);
    email.setHotel(hotel);
    email.setContactValue(hotelContactsDto.getEmail());
    contactRepository.save(email);
    contacts.add(email);
    return contacts;
  }
}
