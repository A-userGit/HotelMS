package com.hotelms.repository;

import com.hotelms.entity.Contact;
import com.hotelms.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "from contacts as c where c.hotel.id = :hotelId and c.type in :contactTypes")
    List<Contact> getByHotelId(Long hotelId, List<ContactType>  contactTypes);
}
