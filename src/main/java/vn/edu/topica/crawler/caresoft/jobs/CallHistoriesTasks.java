package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.CallService;

/**
 * Scheduled tasks for crawling CallLog data
 */
@Component
@Slf4j
public class CallHistoriesTasks {

  @Autowired
  private CallService callService;

  @Scheduled(cron = "${caresoft.crawler.cron.hourly}")
  public void importHourlyCallLog() {
    log.info("Importing Call Log Data");
    crawlData();
  }

  private void crawlData() {
    try {
      callService.importData();
    } catch (Exception e) {
      log.error("Importing Call Log Data failed: " + e);
    }
  }
}