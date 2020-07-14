package vn.edu.topica.crawler.caresoft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Object represents data from TicketLog file
 */
@Data
@Entity
@Table(name = "TicketLog")
public class TicketLog {

    @Id
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "ticket_no")
    private long ticketNo;

    @Column(name = "requester_id")
    private long requesterId;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "assignee_id")
    private long assigneeId;

    @Column(name = "ticket_priority")
    private String ticketPriority;

    @Column(name = "ticket_source")
    private String ticketSource; //Loai (phieu ghi, cuoc goi)

    @Column(name = "ticket_status")
    private String ticketStatus;

    @Column(name = "ticket_subject", columnDefinition = "TEXT")
    private String ticketSubject;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "duedate")
    private String duedate; //Thời điểm chốt

    @Column(name = "feedback_status")
    private String feedbackStatus; //Tình trạng phản hồi

    @Column(name = "automessage_id")
    private String automessage; //Tin nhắn tự động

    @Column(name = "satisfaction")
    private Long satisfaction;

    @Column(name = "satisfaction_at")
    private String satisfactionAt;

    @Column(name = "satisfaction_content")
    private String satisfactionContent;

    @Column(name = "sla")
    private String sla; //Thoi han xu ly

    @Column(name = "sla_id")
    private long slaId;

    @Column(name = "requester_phone")
    private String requesterPhone;

    @Column(name = "requester_email")
    private String requesterEmail;

    @Column(name = "requester_username")
    private String requesterUserName;

    @Column(name = "assignee_phone")
    private String assigneePhone;

    @Column(name = "assignee_role_id")
    private long assigneeRoleId;

    @Column(name = "assignee_email")
    private String assigneeEmail;

    @Column(name = "assignee_username")
    private String assigneeUsername;

    @Column(name = "level_k_truoc")
    private String levelKBefore;

    @Column(name = "level_k_sau")
    private String levelKAfter;

    @Column(name = "khoa_hoc")
    private String packageCode;

    @Column(name = "phong_ban")
    private String department;

    @Column(name = "phan_loai_phieu_ghi")
    private String classifyTicket;

    @Column(name = "phan_loai_nguyen_nhan_cho_cac_don_dac_thu")
    private String classifyReason;

    @Column(name = "tinh_chat_don_hang")
    private String ticketQuality;


}
