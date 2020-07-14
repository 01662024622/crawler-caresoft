package vn.edu.topica.crawler.caresoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.crawler.caresoft.entities.TicketLog;

public interface TicketLogRepository extends JpaRepository<TicketLog, Long> {
}
