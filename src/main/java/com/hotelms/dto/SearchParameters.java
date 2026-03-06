package com.hotelms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchParameters {
    private String name;
    private String city;
    private String brand;
    private String country;
    private List<String> amenities;
}
