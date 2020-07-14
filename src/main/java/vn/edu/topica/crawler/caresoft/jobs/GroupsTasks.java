package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.GroupsService;

/**
 * Scheduled tasks for crawling Groups data
 */
@Component
@Slf4j
public class GroupsTasks {

  @Autowired
  private GroupsService groupsService;

  @Scheduled(cron = "${caresoft.crawler.cron.daily}")
  public void importHourlyContacts() {
    log.info("Importing Groups Data");
    crawlData();
  }

  private void crawlData() {
    try {
      groupsService.importData();
    } catch (Exception e) {
      log.error("Importing Groups Data failed: " + e);
    }
  }
}