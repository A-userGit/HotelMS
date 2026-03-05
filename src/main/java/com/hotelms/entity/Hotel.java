package com.hotelms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@Entity(name = "hotels")
public class Hotel {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotels_seq")
  @SequenceGenerator(name = "hotels_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "brand")
  private String brand;

  @OneToOne(mappedBy = "hotel")
  private Address address;

  @OneToMany(mappedBy = "hotel", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Contact> contacts;

  @ManyToMany
  @JoinTable(
      name = "hotels_amenities",
      joinColumns = @JoinColumn(name = "hotel_id"),
      inverseJoinColumns = @JoinColumn(name = "amenity_id"))
  private List<Amenity> amenities;

  @OneToMany(mappedBy = "hotel", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<TimeTableMark> timeTableMarks;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Hotel hotel = (Hotel) o;
    return id == hotel.id
        && Objects.equals(name, hotel.name)
        && Objects.equals(description, hotel.description)
        && Objects.equals(brand, hotel.brand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, brand);
  }
}
