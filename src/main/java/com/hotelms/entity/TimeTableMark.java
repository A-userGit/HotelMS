package com.hotelms.entity;

import com.hotelms.enums.TimeMarkTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "time_table_marks")
public class TimeTableMark {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_t_marks_seq")
  @SequenceGenerator(name = "t_t_marks_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private TimeMarkTypes type;

  @Column(name = "time_mark")
  private Time timeMark;

  @Column(name = "required")
  private boolean required;

  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TimeTableMark that = (TimeTableMark) o;
    return required == that.required
        && Objects.equals(id, that.id)
        && type == that.type
        && Objects.equals(timeMark, that.timeMark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, timeMark, required);
  }
}
