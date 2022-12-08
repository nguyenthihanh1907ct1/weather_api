package vn.com.pro.weather.domain.meteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastResponse {
  private Detail hourly;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Detail {
    private List<LocalDateTime> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature;
  }
}
