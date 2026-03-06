package com.hotelms.unit.service.impl;

import com.hotelms.dto.AddressDto;
import com.hotelms.dto.ArrivalTimeDto;
import com.hotelms.dto.CreateHotelDto;
import com.hotelms.dto.HistogramDto;
import com.hotelms.dto.HotelContactsDto;
import com.hotelms.dto.HotelShortDBDto;
import com.hotelms.dto.SearchParameters;
import com.hotelms.entity.Address;
import com.hotelms.entity.Amenity;
import com.hotelms.entity.Hotel;
import com.hotelms.enums.HistogramType;
import com.hotelms.exception.EntityNotFoundException;
import com.hotelms.mapper.HotelMapper;
import com.hotelms.repository.HotelRepository;
import com.hotelms.service.AddressService;
import com.hotelms.service.AmenityService;
import com.hotelms.service.ContactService;
import com.hotelms.service.TimeTableMarkService;
import com.hotelms.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for hotel service")
@ExtendWith(MockitoExtension.class)
public class HotelServiceImplUnitTest {

  @Mock private HotelRepository hotelRepository;
  @Mock private AddressService addressService;
  @Mock private ContactService contactService;
  @Mock private TimeTableMarkService timeTableMarkService;
  @Mock private AmenityService amenityService;
  @Mock private HotelMapper hotelMapper;

  @InjectMocks HotelServiceImpl hotelService;

  @Test
  @DisplayName("Try to add hotel")
  public void addHotelTest() {
    CreateHotelDto createHotelDto = new CreateHotelDto();
    createHotelDto.setName("Test Hotel");
    createHotelDto.setContacts(new HotelContactsDto());
    createHotelDto.setBrand("Test Brand");
    createHotelDto.setDescription("Test Description");
    createHotelDto.setAddress(new AddressDto());
    createHotelDto.setArrivalTime(new ArrivalTimeDto());
    Hotel hotel = getHotel(1);
    when(hotelMapper.toHotel(any(CreateHotelDto.class))).thenReturn(hotel);
    when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
    when(addressService.addAddress(any(AddressDto.class), any(Hotel.class)))
        .thenReturn(new Address());
    when(contactService.addHotelContacts(any(HotelContactsDto.class), any(Hotel.class)))
        .thenReturn(new ArrayList<>());
    when(timeTableMarkService.addArrivalTimeToHotel(any(ArrivalTimeDto.class), any(Hotel.class)))
        .thenReturn(new ArrayList<>());
    hotelService.addHotel(createHotelDto);
    verify(hotelRepository, times(1)).save(hotel);
    verify(addressService, times(1)).addAddress(any(AddressDto.class), any(Hotel.class));
    verify(contactService, times(1))
        .addHotelContacts(any(HotelContactsDto.class), any(Hotel.class));
    verify(timeTableMarkService, times(1))
        .addArrivalTimeToHotel(any(ArrivalTimeDto.class), any(Hotel.class));
    verify(hotelMapper, times(1)).toHotelShortDto(any(Hotel.class));
  }

  @Test
  @DisplayName("Try to get all hotels short")
  public void getAllTest() {
    List<Hotel> hotels = new ArrayList<>(3);
    hotels.add(getHotel(1));
    hotels.add(getHotel(2));
    hotels.add(getHotel(3));
    when(hotelRepository.findAll()).thenReturn(hotels);
    hotelService.getAll();
    verify(hotelRepository, times(1)).findAll();
    verify(hotelMapper, times(3)).toHotelShortDto(any(Hotel.class));
  }

  @Test
  @DisplayName("Try to get hotel that doesn't exist")
  public void getByIdTestFail() {
    when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());
    EntityNotFoundException entityNotFoundException =
        assertThrows(EntityNotFoundException.class, () -> hotelService.getById(1L));
    assertEquals(
        "Entity Hotel with value of id = 1 is not found", entityNotFoundException.getMessage());
  }

  @Test
  @DisplayName("Try to get hotel that doesn't exist")
  public void getByIdTestSuccess() {
    when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(getHotel(1)));
    hotelService.getById(1L);
    verify(hotelRepository, times(1)).findById(1L);
    verify(hotelMapper, times(1)).toFullHotelDto(any(Hotel.class));
  }

  @Test
  @DisplayName("Try to get hotels by parameters ")
  public void getByParametersTest() {
    List<HotelShortDBDto> hotelsDB = new ArrayList<>(3);
    hotelsDB.add(new HotelShortDBDto());
    SearchParameters searchParameters = new SearchParameters();
    List<String> amenities = new ArrayList<>();
    amenities.add("\"Test Amenity\"");
    SearchParameters spyParams = spy(searchParameters);
    spyParams.setAmenities(amenities);
    when(hotelRepository.findAllHotelsByParameters(spyParams)).thenReturn(hotelsDB);
    hotelService.getByParameters(spyParams);
    verify(hotelRepository, times(1)).findAllHotelsByParameters(spyParams);
    verify(hotelMapper, times(1)).toHotelShortDto(any(HotelShortDBDto.class));
    assertEquals("test amenity", spyParams.getAmenities().get(0));
  }

  @Test
  @DisplayName("Try to get histogram ")
  public void getHistogramTest() {
    List<HistogramDto> histList = new ArrayList<>();
    histList.add(new HistogramDto("test1", 12));
    histList.add(new HistogramDto("test2", 3));
    when(hotelRepository.findAllHistogramsByParameter(HistogramType.CITY)).thenReturn(histList);
    HashMap<String, Long> histogram = hotelService.getHistogram(HistogramType.CITY);
    verify(hotelRepository, times(1)).findAllHistogramsByParameter(HistogramType.CITY);
    assertEquals(12, histogram.get("test1"));
    assertEquals(3, histogram.get("test2"));
  }

  @Test
  @DisplayName("Try to set amenities")
  public void setAmenitiesTestFail() {
    List<String> amenities = new ArrayList<>();
    amenities.add("\"Test Amenity\"");
    when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
    EntityNotFoundException entityNotFoundException =
        assertThrows(EntityNotFoundException.class, () -> hotelService.setAmenities(amenities, 1L));
    assertEquals(
        "Entity Hotel with value of Id = 1 is not found", entityNotFoundException.getMessage());
  }

  @Test
  @DisplayName("Try to set amenities")
  public void setAmenitiesTestSuccess() {
    List<String> amenitiesNames = new ArrayList<>();
    amenitiesNames.add("\"Test Amenity\"");
    List<Amenity> amenities = new ArrayList<>();
    Amenity amenity = new Amenity();
    amenity.setName("Test Amenity");
    amenities.add(amenity);
    when(hotelRepository.findById(1L)).thenReturn(Optional.of(getHotel(1)));
    when(amenityService.addAmenities(amenitiesNames)).thenReturn(amenities);
    hotelService.setAmenities(amenitiesNames, 1L);
    verify(hotelRepository, times(1)).findById(1L);
    verify(amenityService, times(1)).addAmenities(amenitiesNames);
  }

  private Hotel getHotel(int number) {
    Hotel hotel = new Hotel();
    hotel.setId(number);
    hotel.setName("Test Hotel" + number);
    hotel.setBrand("Test Brand" + number);
    hotel.setDescription("Test Description" + number);
    return hotel;
  }
}
