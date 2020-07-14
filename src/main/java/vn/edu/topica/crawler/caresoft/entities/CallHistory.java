package vn.edu.topica.crawler.caresoft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Calls")
public class CallHistory {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "call_id")
    private String callId;

    @Column(name = "path", columnDefinition = "TEXT")
    private String path;

    @Column(name = "path_download", columnDefinition = "TEXT")
    private String pathDownload;

    @Column(name = "caller")
    private String caller;

    @Column(name = "called")
    private String called;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "agent_id")
    private String agentId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "call_type")
    private Long callType;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "call_status")
    private String callStatus;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "wait_time")
    private String waitTime;

    @Column(name = "hold_time")
    private String holdTime;

    @Column(name = "talk_time")
    private String talkTime;

    @Column(name = "end_status")
    private String endStatus;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "last_agent_id")
    private String lastAgentId;

    @Column(name = "last_user_id")
    private long lastUserId;

    @Column(name = "call_survey")
    private String callSurvey;

    @Column(name = "call_survey_result")
    private String callSurveyResult;

    public CallHistory() {
    }
}
