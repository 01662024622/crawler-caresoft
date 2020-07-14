package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.TicketService;

/**
 * Scheduled tasks for crawling TicketLog data
 */
@Component
@Slf4j
public class TicketLogBackupTasks {

  @Autowired
  private TicketService ticketService;

  @Scheduled(cron = "${caresoft.backup.crawler.cron.hourly}")
  public void importHourlyTicketLog() {
    log.info("[Backup]: Importing Ticket Log Data");
    crawlData();
  }

  private void crawlData() {
    try {
      ticketService.backup();
    } catch (Exception e) {
      log.error("[Backup]: Importing Ticket Log Data failed: " + e);
    }
  }
}