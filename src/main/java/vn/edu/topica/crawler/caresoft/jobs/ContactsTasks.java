package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.ContactsService;

/**
 * Scheduled tasks for crawling Contacts data
 */
@Component
@Slf4j
public class ContactsTasks {

  @Autowired
  private ContactsService contactsService;

  @Scheduled(cron = "${caresoft.crawler.cron.hourly}")
  public void importHourlyContacts() {
    log.info("Importing Contacts Data");
    crawlData();
  }

  private void crawlData() {
    try {
      contactsService.importData();
    } catch (Exception e) {
      log.error("Importing Contacts Data failed: " + e);
    }
  }
}