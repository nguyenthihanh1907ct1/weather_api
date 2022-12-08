package vn.com.pro.weather.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.pro.weather.application.port.WeatherServicePort;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/weather")
public class IndexController {
  final WeatherServicePort weatherServicePort;

  @GetMapping(value = "/today")
  public ResponseEntity<String> index() {

    return ResponseEntity.ok(weatherServicePort.findRangerOnDay());
  }
}
