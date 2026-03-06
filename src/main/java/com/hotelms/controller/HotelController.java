package com.hotelms.controller;

import com.hotelms.dto.*;
import com.hotelms.enums.HistogramType;
import com.hotelms.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/property-view/")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("hotels")
    public ResponseEntity<HotelShortDto> createHotel(@RequestBody CreateHotelDto createHotelDto) {
        HotelShortDto hotelShortDto = hotelService.addHotel(createHotelDto);
        return new ResponseEntity<>(hotelShortDto, HttpStatus.CREATED);
    }

    @GetMapping("hotels")
    public ResponseEntity<List<HotelShortDto>> getAllHotels() {
        List<HotelShortDto> hotels = hotelService.getAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("hotels/{id}")
    public ResponseEntity<FullHotelDto> getHotelInfo(@PathVariable String id) {
        FullHotelDto hotel = hotelService.getById(Long.valueOf(id));
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<HotelShortDto>> getHotelsByParameters(SearchParameters searchParameters) {
        List<HotelShortDto> hotelsFound = hotelService.getByParameters(searchParameters);
        return new ResponseEntity<>(hotelsFound, HttpStatus.OK);
    }

    @GetMapping("histogram/{param}")
    public ResponseEntity<HashMap<String,Long>> getHistogramsByParameters(@PathVariable String param) {
        HistogramType histogramType = HistogramType.valueOf(param.toUpperCase());
        HashMap<String,Long> histograms = hotelService.getHistogram(histogramType);
        return new ResponseEntity<>(histograms, HttpStatus.OK);
    }

    @PostMapping("hotels/{id}/amenities")
    public ResponseEntity setAmenitiesForHotel(@PathVariable String id, @RequestBody List<String> amenities) {
        long hotelId = Long.parseLong(id);
        hotelService.setAmenities(amenities, hotelId);
        return ResponseEntity.noContent().build();
    }


}
