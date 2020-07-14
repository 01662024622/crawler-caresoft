package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.ServicesService;

/**
 * Scheduled tasks for crawling Services data
 */
@Component
@Slf4j
public class ServicesTasks {

  @Autowired
  private ServicesService servicesService;

  @Scheduled(cron = "${caresoft.crawler.cron.daily}")
  public void importHourlyContacts() {
    log.info("Importing Services Data");
    crawlData();
  }

  private void crawlData() {
    try {
      servicesService.importData();
    } catch (Exception e) {
      log.error("Importing Services Data failed: " + e);
    }
  }
}