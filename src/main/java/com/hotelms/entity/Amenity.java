package com.hotelms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "amenities")
public class Amenity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amenities_seq")
  @SequenceGenerator(name = "amenities_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "amenities")
  private List<Hotel> hotels;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Amenity amenity = (Amenity) o;
    return id == amenity.id && Objects.equals(name, amenity.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
