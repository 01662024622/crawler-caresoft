package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.AgentsService;

/**
 * Scheduled tasks for crawling Agents data
 */
@Component
@Slf4j
public class AgentsTasks {

  @Autowired
  private AgentsService agentsService;

  @Scheduled(cron = "${caresoft.crawler.cron.daily}")
  public void importHourlyContacts() {
    log.info("Importing Agents Data");
    crawlData();
  }

  private void crawlData() {
    try {
      agentsService.importData();
    } catch (Exception e) {
      log.error("Importing Agents Data failed: " + e);
    }
  }
}