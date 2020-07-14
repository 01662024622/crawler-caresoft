package vn.edu.topica.crawler.caresoft.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.edu.topica.crawler.caresoft.services.iservice.CustomFieldService;

/**
 * Scheduled tasks for crawling CustomFields data
 */
@Component
@Slf4j
public class CustomFieldsTasks {

    @Autowired
    private CustomFieldService customFieldService;

    @Scheduled(cron = "${caresoft.crawler.cron.daily}")
    public void importData() {
        log.info("Importing Custom Field Data");
        crawlData();
    }

    private void crawlData() {
        try {
            customFieldService.importData();
        } catch (Exception e) {
            log.error("Importing Custom Field Data failed: " + e);
        }
    }
}