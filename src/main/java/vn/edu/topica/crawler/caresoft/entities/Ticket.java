package vn.edu.topica.crawler.caresoft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tickets")
public class Ticket {

  @Id
  @Column(name = "ticket_no")
  private Long ticketNo;

  @Column(name = "ticket_id")
  private Long ticketId;

  @Column(name = "ticket_status")
  private String ticketStatus;

  @Column(name = "ticket_subject", columnDefinition = "TEXT")
  private String ticketSubject;

  @Column(name = "ticket_source")
  private String ticketSource;

  @Column(name = "ticket_priority")
  private String ticketPriority;

  @Column(name = "requester_id")
  private Long requesterId;

  @Column(name = "assignee_id")
  private Long assigneeId;

  @Column(name = "created_at")
  private String createdAt;

  @Column(name = "updated_at")
  private String updatedAt;

  public Ticket() {
  }
}
