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
public class ContactsBackupTasks {

    @Autowired
    private ContactsService contactsService;

    @Scheduled(cron = "${caresoft.backup.crawler.cron.hourly}")
    public void importHourlyTicketLog() {
        log.info("[Backup]: Importing Contact Data");
        crawlData();
    }

    private void crawlData() {
        try {
            contactsService.backup();
        } catch (Exception e) {
            log.error("[Backup]: Importing Contact Data failed: " + e);
        }
    }
}