package vn.com.pro.weather.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.pro.weather.domain.entity.WeatherEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
  Optional<WeatherEntity> findFirstByWeatherDateOrderByIdDesc(LocalDate weatherDate);
}
