package vn.com.pro.weather.application.port;

import vn.com.pro.weather.domain.entity.WeatherEntity;
import vn.com.pro.weather.domain.meteo.WeatherForecastResponse;

import java.util.List;

public interface WeatherServicePort {
    String findRangerOnDay();
    void syncWeatherOnNextWeek();
    WeatherForecastResponse fetchWeatherDataOnNextWeek();

    List<WeatherEntity> mapToEntity(WeatherForecastResponse response);
}
