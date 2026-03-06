package com.hotelms.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hotelms.HotelMSApplication;
import com.hotelms.dto.AddressDto;
import com.hotelms.dto.ArrivalTimeDto;
import com.hotelms.dto.CreateHotelDto;
import com.hotelms.dto.HotelContactsDto;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = HotelMSApplication.class)
public class HotelControllerIntegrationTest {

  @Autowired MockMvc mockMvc;

  @Test
  @DisplayName("Integrational get all hotels test")
  public void getHotels() {
    try {
      mockMvc.perform(MockMvcRequestBuilders.get("/property-view/hotels")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(5)))
              .andExpect(jsonPath("$[0].address").value("14 testS4, test_city14, code, testB3"));
    } catch (Exception e) {
      fail("Exception during hotels get all mvc test " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Integrational get hotel full info test")
  @Sql("classpath:/sql/seed-data.sql")
  public void getHotelFullInfo() {
    try {
      mockMvc.perform(MockMvcRequestBuilders.get("/property-view/hotels/{id}", -1)
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.address.city").value("test_city1"));
    } catch (Exception e) {
      fail("Exception during hotels get all mvc test " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Integrational get hotel histogram test")
  //@Sql("classpath:/sql/seed-data.sql")
  public void getHotelHistogram() {
    try {
      String city = mockMvc.perform(MockMvcRequestBuilders.get("/property-view/histogram/{param}", "city")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.test_city1").value("2"))
              .andReturn().getResponse().getContentAsString();
    } catch (Exception e) {
      fail("Exception during hotels get all mvc test " + e.getMessage());
    }
  }


  @Test
  @DisplayName("Integrational hotel create mvc test")
  public void createHotel() {
    try {
      CreateHotelDto createHotelDto = getCreateHotelDto();
      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String hotelInfoJSON = ow.writeValueAsString(createHotelDto);
      mockMvc
              .perform(
                      MockMvcRequestBuilders.post("/property-view/hotels")
                              .content(hotelInfoJSON)
                              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value("1"))
              .andExpect(jsonPath("$.phone").value("+234568 433 2"));
    } catch (Exception e) {
      fail("Exception during hotel create mvc test " + e.getMessage());
    }
  }

  private static @NonNull CreateHotelDto getCreateHotelDto() {
    CreateHotelDto createHotelDto = new CreateHotelDto();
    createHotelDto.setName("Test Hotel");
    createHotelDto.setBrand("Test Brand");
    createHotelDto.setDescription("Test Description");
    AddressDto addressDto = new AddressDto();
    addressDto.setCity("Test City");
    addressDto.setCountry("Test Country");
    addressDto.setStreet("Test Street");
    addressDto.setPostCode("231456");
    addressDto.setHouseNumber(12);
    createHotelDto.setAddress(addressDto);
    HotelContactsDto hotelContactsDto = new HotelContactsDto();
    hotelContactsDto.setPhone("+234568 433 2");
    hotelContactsDto.setEmail("email@mail.com");
    createHotelDto.setContacts(hotelContactsDto);
    ArrivalTimeDto arrivalTimeDto = new ArrivalTimeDto();
    arrivalTimeDto.setCheckIn("12:00");
    arrivalTimeDto.setCheckOut("12:00");
    createHotelDto.setArrivalTime(arrivalTimeDto);
    return createHotelDto;
  }


}
