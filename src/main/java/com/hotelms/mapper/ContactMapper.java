package com.hotelms.mapper;

import com.hotelms.dto.HotelContactsDto;
import com.hotelms.entity.Contact;
import com.hotelms.enums.ContactType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
  default HotelContactsDto toHotelContactsDto(List<Contact> contacts) {
    HotelContactsDto hotelContactsDto = new HotelContactsDto();
    contacts.forEach(
        contact -> {
          if (contact.getType() == ContactType.EMAIL) {
            hotelContactsDto.setEmail(contact.getContactValue());
          }
          if (contact.getType() == ContactType.PHONE) {
            hotelContactsDto.setPhone(contact.getContactValue());
          }
        });
    return hotelContactsDto;
  }

  @Named("phoneFromContacts")
  default String toHotelShortDto(List<Contact> contacts) {
    for (Contact contact : contacts) {
      if (contact.getType() == ContactType.PHONE) {
        return contact.getContactValue();
      }
    }
    return null;
  }
}
