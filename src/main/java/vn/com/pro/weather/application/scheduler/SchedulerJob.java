package vn.com.pro.weather.application.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.com.pro.weather.application.port.WeatherServicePort;

@Component
@Slf4j
@AllArgsConstructor
public class SchedulerJob {
  final WeatherServicePort weatherServicePort;

  @Scheduled(cron = "${cron.job.pull.data.in.week}")
  public void cronJobPullWeatherWeekData() {
    log.info("scheduled job pull data weather on next week start");
    try {
      weatherServicePort.fetchWeatherDataOnNextWeek();
    } catch (Exception exception) {
      log.error("job pull data error: {}", exception.getLocalizedMessage());
    }
  }
}
