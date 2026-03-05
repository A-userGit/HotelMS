package com.hotelms.repository.impl;

import com.hotelms.dto.SearchParameters;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SearchHotelQueryBuilder {
  private final StringBuilder queryBuilder;
  boolean first = true;

  public SearchHotelQueryBuilder(SearchParameters searchParameters) {
    queryBuilder = new StringBuilder();
    queryBuilder.append(
        "SELECT DISTINCT hotel.id as id, hotel.name as name, hotel.description as description, contacts.contactValue as phone, hotel.address as address FROM hotels as hotel LEFT OUTER JOIN hotel.contacts as contacts");
    if (!StringUtils.isBlank(searchParameters.getCity())
        || !StringUtils.isBlank(searchParameters.getCountry())) {
      queryBuilder.append(" INNER JOIN hotel.address as address ");
    }
    if (searchParameters.getAmenities() != null && !searchParameters.getAmenities().isEmpty()) {
      queryBuilder.append(" INNER JOIN hotel.amenities as amenity ");
    }
  }

  public static SearchHotelQueryBuilder createSearchHotelQueryBuilder(
      SearchParameters searchParameters) {
    return new SearchHotelQueryBuilder(searchParameters);
  }

  public String build() {
    return queryBuilder.toString();
  }

  private SearchHotelQueryBuilder and() {
    if (first) {
      first = false;
      queryBuilder.append(" WHERE lower(contacts.type) LIKE :phone AND ");
      return this;
    }
    queryBuilder.append(" AND ");
    return this;
  }

  private SearchHotelQueryBuilder filterStringParam(String param, String condition) {
    if (StringUtils.isBlank(param)) {
      return this;
    }
    and();
    queryBuilder.append(condition);
    return this;
  }

  public SearchHotelQueryBuilder filterName(String name) {
    return filterStringParam(name, " lower(hotel.name) LIKE :name");
  }

  public SearchHotelQueryBuilder filterBrand(String brand) {
    return filterStringParam(brand, " lower(hotel.brand) LIKE :brand ");
  }

  public SearchHotelQueryBuilder filterCity(String city) {
    return filterStringParam(city, " lower(address.city) LIKE CONCAT('%',:city,'%') ");
  }

  public SearchHotelQueryBuilder filterCountry(String country) {
    return filterStringParam(country, " lower(address.country) LIKE CONCAT('%',:country,'%') ");
  }

  public SearchHotelQueryBuilder filterAmenities(List<String> amenities) {
    if (amenities == null || amenities.isEmpty()) {
      return this;
    }
    and();
    queryBuilder.append("lower(amenity.name) IN (:amenities)");
    return this;
  }
}
