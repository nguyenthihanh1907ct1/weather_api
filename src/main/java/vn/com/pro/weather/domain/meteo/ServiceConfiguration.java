package vn.com.pro.weather.domain.meteo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ServiceConfiguration {

  @Value("${weather.meteo.host}")
  private String host;

  @Value("${weather.meteo.latitude}")
  private Double latitude;

  @Value("${weather.meteo.longitude}")
  private Double longitude;

  @Value("${weather.meteo.hourly}")
  private String hourly;

  @Value("${weather.meteo.timezone}")
  private String timezone;
}
