package com.hotelms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@Entity(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_seq")
  @SequenceGenerator(name = "addresses_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "city")
  private String city;

  @Column(name = "street")
  private String street;

  @Column(name = "postcode")
  private String postCode;

  @Column(name = "country")
  private String country;

  @Column(name = "house_number")
  private int houseNumber;

  @OneToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return id == address.id
        && houseNumber == address.houseNumber
        && Objects.equals(city, address.city)
        && Objects.equals(street, address.street)
        && Objects.equals(postCode, address.postCode)
        && Objects.equals(country, address.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, city, street, postCode, country, houseNumber);
  }
}
