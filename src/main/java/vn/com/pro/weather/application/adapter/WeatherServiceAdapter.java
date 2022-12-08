package vn.com.pro.weather.application.adapter;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vn.com.pro.weather.application.port.WeatherServicePort;
import vn.com.pro.weather.domain.entity.WeatherEntity;
import vn.com.pro.weather.domain.meteo.ServiceConfiguration;
import vn.com.pro.weather.domain.meteo.WeatherForecastResponse;
import vn.com.pro.weather.infrastructure.repository.WeatherRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherServiceAdapter implements WeatherServicePort {
  final RestTemplate restTemplate;
  final WeatherRepository weatherRepository;
  final ServiceConfiguration serviceConfiguration;

  @Override
  public String findRangerOnDay() {
    return weatherRepository
        .findFirstByWeatherDateOrderByIdDesc(LocalDate.now())
        .map(
            weather ->
                String.format(
                    "%s là thời điểm lạnh nhất, %s là thời điểm ấm nhất.",
                    weather.getHourlyMin(), weather.getHourlyMax()))
        .orElse("Chưa có dữ liệu!");
  }

  @Override
  public void syncWeatherOnNextWeek() {
    WeatherForecastResponse response = fetchWeatherDataOnNextWeek();
    weatherRepository.saveAll(mapToEntity(response));
  }

  @Override
  public WeatherForecastResponse fetchWeatherDataOnNextWeek() {
    HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
    String host =
        UriComponentsBuilder.fromHttpUrl(serviceConfiguration.getHost())
            .queryParam("latitude", serviceConfiguration.getLatitude())
            .queryParam("longitude", serviceConfiguration.getLongitude())
            .queryParam("hourly", serviceConfiguration.getHourly())
            .queryParam("timezone", serviceConfiguration.getTimezone())
            .build()
            .toUriString();
    ResponseEntity<WeatherForecastResponse> response =
        restTemplate.exchange(host, HttpMethod.GET, entity, WeatherForecastResponse.class);
    return response.getBody();
  }

  @Override
  public List<WeatherEntity> mapToEntity(WeatherForecastResponse response) {
    List<List<LocalDateTime>> week = Lists.partition(response.getHourly().getTime(), 24);
    List<List<Double>> temp = Lists.partition(response.getHourly().getTemperature(), 24);
    log.info("temp size: {}", temp.size());
    return IntStream.range(0, temp.size())
        .mapToObj(
            i -> {
              List<Double> doubles = temp.get(i);
              List<LocalDateTime> times = week.get(i);
              Double min =
                  doubles.stream().min(Comparator.comparingDouble(Double::doubleValue)).orElse(0d);
              Double max =
                  doubles.stream().max(Comparator.comparingDouble(Double::doubleValue)).orElse(0d);
              int indexOfMin = doubles.indexOf(min);
              int indexOfMax = doubles.indexOf(max);
              LocalDateTime timeAtMin = times.get(indexOfMin);
              LocalDateTime timeAtMax = times.get(indexOfMax);
              return WeatherEntity.builder()
                  .weatherDate(timeAtMax.toLocalDate())
                  .temperatureMin(min)
                  .hourlyMin(timeAtMin.toLocalTime())
                  .temperatureMax(max)
                  .hourlyMax(timeAtMax.toLocalTime())
                  .createdAt(LocalDateTime.now())
                  .build();
            })
        .collect(Collectors.toList());
  }
}
