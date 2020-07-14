package vn.edu.topica.crawler.caresoft.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.topica.crawler.caresoft.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  @Query("SELECT max(ticket.updatedAt) FROM Ticket AS ticket ")
  Object findLastUpdatedAt();

  @Query("SELECT ticket.ticketId FROM Ticket ticket WHERE ticket.ticketId NOT IN (SELECT DISTINCT ticketLog.ticketId FROM TicketLog ticketLog)")
  List<Long> findIdNotInTicketLog();
}
