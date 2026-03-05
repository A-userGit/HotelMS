package com.hotelms.mapper;

import com.hotelms.dto.AddressDto;
import com.hotelms.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  AddressDto toAddressDto(Address address);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "hotel", ignore = true)
  Address toAddress(AddressDto addressDto);

  @Named("addressStringified")
  default String toAddressString(Address address) {
    return String.format(
        "%d %s, %s, %s, %s",
        address.getHouseNumber(),
        address.getStreet(),
        address.getCity(),
        address.getPostCode(),
        address.getCountry());
  }
}
