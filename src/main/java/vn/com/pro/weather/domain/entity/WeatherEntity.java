package vn.com.pro.weather.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Weather")
public class WeatherEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    private LocalDate weatherDate;
    private LocalTime hourlyMax;
    private LocalTime hourlyMin;
    private Double temperatureMax;
    private Double temperatureMin;
    private LocalDateTime createdAt;
}
