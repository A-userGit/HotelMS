package com.hotelms.entity;

import com.hotelms.enums.ContactType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@Entity(name = "contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_seq")
  @SequenceGenerator(name = "contacts_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private ContactType type;

  @Column(name = "contactValue")
  private String contactValue;

  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;
}
