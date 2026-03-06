package com.hotelms.service.impl;

import com.hotelms.dto.*;
import com.hotelms.entity.*;
import com.hotelms.enums.HistogramType;
import com.hotelms.exception.EntityNotFoundException;
import com.hotelms.mapper.HotelMapper;
import com.hotelms.repository.HotelRepository;
import com.hotelms.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AddressService addressService;
    private final ContactService contactService;
    private final TimeTableMarkService timeTableMarkService;
    private final AmenityService amenityService;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional
    public HotelShortDto addHotel(CreateHotelDto createHotelDto) {
        Hotel hotelData = hotelMapper.toHotel(createHotelDto);
        Hotel hotel = hotelRepository.save(hotelData);
        Address address = addressService.addAddress(createHotelDto.getAddress(),  hotel);
        List<Contact> contacts = contactService.addHotelContacts(createHotelDto.getContacts(), hotel);
        List<TimeTableMark> timeTableMarks = timeTableMarkService.addArrivalTimeToHotel(createHotelDto.getArrivalTime(), hotel);
        hotel.setAddress(address);
        hotel.setContacts(contacts);
        hotel.setTimeTableMarks(timeTableMarks);
        return hotelMapper.toHotelShortDto(hotel);
    }

    @Override
    public List<HotelShortDto> getAll() {
        return hotelRepository.findAll().stream().map(hotelMapper::toHotelShortDto).toList();
    }

    @Override
    public FullHotelDto getById(Long aLong) {
        Optional<Hotel> byId = hotelRepository.findById(aLong);
        if(byId.isEmpty()) {
            throw EntityNotFoundException.entityNotFound("Hotel", "id", aLong.toString());
        }
        return hotelMapper.toFullHotelDto(byId.get());
    }

    @Override
    public List<HotelShortDto> getByParameters(SearchParameters parameters) {
        List<String> adaptedAmenities = new ArrayList<>();
        if(parameters!=null&&parameters.getAmenities()!=null) {
            parameters.getAmenities().forEach(amenity -> {
                adaptedAmenities.add(amenity.replaceAll("[\\[\\]\"]", "").toLowerCase());
            });
            parameters.setAmenities(adaptedAmenities);
        }
        List<HotelShortDBDto> allHotelsByParameters = hotelRepository.findAllHotelsByParameters(parameters);
        return allHotelsByParameters.stream().map(hotelMapper::toHotelShortDto).toList();
    }

    @Override
    public HashMap<String, Long> getHistogram(HistogramType histogramType) {
        List<HistogramDto> histogramByParameter = hotelRepository.findAllHistogramsByParameter(histogramType);
        HashMap<String,Long> histogramMap = new HashMap();
        for (HistogramDto histogramDto : histogramByParameter) {
            histogramMap.put(histogramDto.getName(), histogramDto.getValue());
        }
        return histogramMap;
    }

    @Override
    @Transactional
    public void setAmenities(List<String> amenitiesNames,  long hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if(hotelOptional.isEmpty()) {
            throw EntityNotFoundException.entityNotFound("Hotel", "Id", String.valueOf(hotelId));
        }
        Hotel hotel = hotelOptional.get();
        List<Amenity> amenities = amenityService.addAmenities(amenitiesNames);
        hotel.setAmenities(amenities);
        hotelRepository.save(hotel);
    }
}
