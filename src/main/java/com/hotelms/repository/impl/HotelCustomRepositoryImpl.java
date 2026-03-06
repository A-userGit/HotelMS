package com.hotelms.repository.impl;

import com.hotelms.dto.HistogramDto;
import com.hotelms.dto.HotelShortDBDto;
import com.hotelms.dto.SearchParameters;
import com.hotelms.enums.HistogramType;
import com.hotelms.mapper.DBQueryMapper;
import com.hotelms.repository.HotelCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class HotelCustomRepositoryImpl implements HotelCustomRepository {

  @PersistenceContext private EntityManager entityManager;
  private final DBQueryMapper dbQueryMapper;

  @Override
  public List<HotelShortDBDto> findAllHotelsByParameters(SearchParameters searchParameters) {
    String queryString =
        SearchHotelQueryBuilder.createSearchHotelQueryBuilder(searchParameters)
            .filterName(searchParameters.getName())
            .filterBrand(searchParameters.getBrand())
            .filterCity(searchParameters.getCity())
            .filterCountry(searchParameters.getCountry())
            .filterAmenities(searchParameters.getAmenities())
            .build();
    Query query = entityManager.createQuery(queryString);
    setQueryStringParameter("phone", "PHONE", query);
    setQueryStringParameter("name", searchParameters.getName(), query);
    setQueryStringParameter("brand", searchParameters.getBrand(), query);
    setQueryStringParameter("city", searchParameters.getCity(), query);
    setQueryStringParameter("country", searchParameters.getCountry(), query);
    setQueryStringArrayParameter("amenities", searchParameters.getAmenities(), query);
    List<Object[]> results = query.getResultList();
    return results.stream().map(dbQueryMapper::toShortDBHotel).toList();
  }

  @Override
  public List<HistogramDto> findAllHistogramsByParameter(HistogramType parameter) {
    String histParamName = "";
    String joinValue = "";

    switch (parameter) {
      case CITY -> {
        histParamName = "address.city";
        joinValue = "INNER JOIN hotel.address as address";
      }
      case COUNTRY -> {
        histParamName = "address.country";
        joinValue = "INNER JOIN hotel.address as address";
      }
      case AMENITIES -> {
        histParamName = "amenity.name";
        joinValue = "INNER JOIN hotel.amenities as amenity";
      }
      case BRAND -> {
        histParamName = "h.brand";
        joinValue = "";
      }
    }
    String queryString =
        String.format(
            "select %s as name, count(*) as value from hotels as hotel %s group by %s",
            histParamName, joinValue, histParamName);
    Query query = entityManager.createQuery(queryString);
    List<Object[]> resultList = query.getResultList();
    return resultList.stream().map(dbQueryMapper::toHistogramDto).toList();
  }

  private void setQueryStringParameter(String parameterName, String parameter, Query query) {
    if (StringUtils.isBlank(parameter)) {
      return;
    }
    parameter = parameter.toLowerCase();
    query.setParameter(parameterName, parameter);
  }

  private void setQueryStringArrayParameter(
      String parameterName, List<String> parameter, Query query) {
    if (parameter == null || parameter.isEmpty()) {
      return;
    }
    query.setParameter(parameterName, parameter);
  }
}
