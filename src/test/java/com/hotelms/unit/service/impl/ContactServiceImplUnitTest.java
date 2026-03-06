package com.hotelms.unit.service.impl;

import com.hotelms.dto.HotelContactsDto;
import com.hotelms.entity.Contact;
import com.hotelms.entity.Hotel;
import com.hotelms.enums.ContactType;
import com.hotelms.repository.ContactRepository;
import com.hotelms.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Tests for contacts service")
@ExtendWith(MockitoExtension.class)
public class ContactServiceImplUnitTest {
  @Mock
  private ContactRepository contactRepository;
  @InjectMocks
  private ContactServiceImpl contactService;

  @Test
  @DisplayName("Test add contacts")
  public void addHotelContactsTest() {
    Hotel hotel = new Hotel();
    HotelContactsDto hotelContactsDto = new HotelContactsDto();
    hotelContactsDto.setEmail("email");
    hotelContactsDto.setPhone("phone");
    List<Contact> contacts = contactService.addHotelContacts(hotelContactsDto, hotel);
    verify(contactRepository, times(2)).save(any(Contact.class));
    assertEquals(ContactType.PHONE, contacts.get(0).getType());
  }
}
