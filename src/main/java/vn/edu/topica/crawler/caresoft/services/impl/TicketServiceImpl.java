package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.CustomFieldMapping;
import vn.edu.topica.crawler.caresoft.entities.CustomFields;
import vn.edu.topica.crawler.caresoft.entities.Ticket;
import vn.edu.topica.crawler.caresoft.entities.TicketLog;
import vn.edu.topica.crawler.caresoft.repositories.CustomFieldRepository;
import vn.edu.topica.crawler.caresoft.repositories.TicketLogRepository;
import vn.edu.topica.crawler.caresoft.repositories.TicketRepository;
import vn.edu.topica.crawler.caresoft.services.ServiceContext;
import vn.edu.topica.crawler.caresoft.services.iservice.CustomFieldService;
import vn.edu.topica.crawler.caresoft.services.iservice.TicketService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketLogRepository ticketLogRepository;
    private CustomFieldRepository customFieldRepository;
    private CustomFieldService customFieldService;
    private ServiceContext serviceContext;

    @Autowired
    public TicketServiceImpl(ServiceContext serviceContext,
                             TicketRepository ticketRepository,
                             TicketLogRepository ticketLogRepository,
                             CustomFieldService customFieldService,
                             CustomFieldRepository customFieldRepository) {
        this.serviceContext = serviceContext;
        this.ticketRepository = ticketRepository;
        this.ticketLogRepository = ticketLogRepository;
        this.customFieldRepository = customFieldRepository;
        this.customFieldService = customFieldService;
    }

    @Override
    public void importData() throws UnirestException {
        Object obj = ticketRepository.findLastUpdatedAt();
        String updatedTime = obj == null ? "2018-01-01T00:00:00Z" : obj.toString();
        log.info("Max updated time: " + updatedTime);
        HttpResponse<String> response = getTicketByUpdated(updatedTime);
        JSONObject res = new JSONObject(response.getBody());
        if (res.get("code").toString().toLowerCase().equals("ok")) {
            List<CustomFields> customFieldsList = this.customFieldRepository.findAll();
            JSONArray tickets = res.getJSONArray("tickets");
            for (Object object : tickets) {
                JSONObject jsonObject = (JSONObject) object;
                Ticket ticket = new Ticket();
                ticket.setTicketNo(jsonObject.getLong("ticket_no"));
                ticket.setTicketId(jsonObject.getLong("ticket_id"));
                ticket.setTicketSource(String.valueOf(jsonObject.get("ticket_source")));
                ticket.setTicketPriority(String.valueOf(jsonObject.get("ticket_priority")));
                ticket.setTicketSubject(String.valueOf(jsonObject.get("ticket_subject")));
                ticket.setTicketStatus(String.valueOf(jsonObject.get("ticket_status")));
                ticket.setRequesterId(jsonObject.getLong("requester_id"));
                if (!jsonObject.isNull("created_at")) {
                    ticket.setCreatedAt(jsonObject.getString("created_at"));
                }
                if (!jsonObject.isNull("updated_at")) {
                    ticket.setUpdatedAt(jsonObject.getString("updated_at"));
                }
                if (!jsonObject.isNull("assignee_id")) {
                    ticket.setAssigneeId(jsonObject.getLong("assignee_id"));
                }
                ticketRepository.save(ticket);
                saveTicketLog(ticket.getTicketId(), customFieldsList);
            }
        }

    }

    private void saveTicketLog(long id, List<CustomFields> customFieldsList) throws UnirestException {
        if (customFieldsList.size() == 0) {
            log.warn("List CustomField is empty !!!");
            customFieldService.importData();
            return;
        }
        HttpResponse<String> resp = getTicketById(id);
        JSONObject jsonRes = new JSONObject(resp.getBody());

        if (!jsonRes.isNull("ticket")) {
            JSONObject jsonObject = jsonRes.getJSONObject("ticket");
            TicketLog ticketLog = new TicketLog();
            ticketLog.setTicketNo(jsonObject.getLong("ticket_no"));
            ticketLog.setTicketId(jsonObject.getLong("ticket_id"));
            ticketLog.setTicketStatus(jsonObject.getString("ticket_status"));
            ticketLog.setTicketPriority(jsonObject.getString("ticket_priority"));
            ticketLog.setTicketSource(jsonObject.getString("ticket_source"));
            ticketLog.setTicketSubject(jsonObject.getString("ticket_subject"));
            ticketLog.setCreatedAt(jsonObject.getString("created_at"));
            ticketLog.setUpdatedAt(jsonObject.getString("updated_at"));
            if (!jsonObject.isNull("account_id")) {
                ticketLog.setAccountId(jsonObject.getLong("account_id"));
            }
            if (!jsonObject.isNull("requester_id")) {
                ticketLog.setRequesterId(jsonObject.getLong("requester_id"));
            }
            if (!jsonObject.isNull("assignee_id")) {
                ticketLog.setAssigneeId(jsonObject.getLong("assignee_id"));
            }
            if (!jsonObject.isNull("group_id")) {
                ticketLog.setGroupId(jsonObject.getInt("group_id"));
            }
            if (!jsonObject.isNull("duedate")) {
                ticketLog.setDuedate(jsonObject.getString("duedate"));
            }
            if (!jsonObject.isNull("satisfaction")) {
                ticketLog.setSatisfaction(jsonObject.getLong("satisfaction"));
            }
            if (!jsonObject.isNull("satisfaction_content")) {
                ticketLog.setSatisfactionContent(jsonObject.getString("satisfaction_content"));
            }
            if (!jsonObject.isNull("satisfaction_at")) {
                ticketLog.setSatisfactionAt(jsonObject.getString("satisfaction_at"));
            }
            if (!jsonObject.isNull("sla")) {
                ticketLog.setSla(jsonObject.getString("sla"));
            }
            if (!jsonObject.isNull("sla_id")) {
                ticketLog.setSlaId(jsonObject.getLong("sla_id"));
            }
            if (!jsonObject.isNull("requester")) {
                JSONObject requester = jsonObject.getJSONObject("requester");
                if (!requester.isNull("phone_no")) {
                    ticketLog.setRequesterPhone(requester.getString("phone_no"));
                }
                if (!requester.isNull("email")) {
                    ticketLog.setRequesterEmail(requester.getString("email"));
                }
                if (!requester.isNull("username")) {
                    ticketLog.setRequesterUserName(requester.getString("username"));
                }
            }
            if (!jsonObject.isNull("assignee")) {
                JSONObject assignee = jsonObject.getJSONObject("assignee");
                if (!assignee.isNull("phone_no")) {
                    ticketLog.setAssigneePhone(assignee.getString("phone_no"));
                }
                if (!assignee.isNull("role_id")) {
                    ticketLog.setAssigneeRoleId(assignee.getInt("role_id"));
                }
                if (!assignee.isNull("email")) {
                    ticketLog.setAssigneeEmail(assignee.getString("email"));
                }
                if (!assignee.isNull("username")) {
                    ticketLog.setAssigneeUsername(assignee.getString("username"));
                }
            }
            if (!jsonObject.isNull("custom_filed")) {
                JSONArray customFields = jsonObject.getJSONArray("custom_filed");
                List<CustomFieldMapping> list = new ArrayList<>();
                for (Object o : customFields) {
                    JSONObject obj = (JSONObject) o;
                    long customFieldId = obj.getLong("id");
                    String customFieldValue = obj.isNull("value") ? null : obj.getString("value");
                    String value = customFieldValue == null ? null : customFieldsList.stream().filter(c -> String.valueOf(c.getId()).equals(customFieldValue)).findAny().orElse(new CustomFields()).getLabel();
                    if (value != null) {
                        switch ((int) customFieldId) {
                            case 2532:
                                ticketLog.setPackageCode(value);
                                break;
                            case 2211:
                                ticketLog.setDepartment(value);
                                break;
                            case 3039:
                                ticketLog.setClassifyTicket(value);
                                break;
                            case 4015:
                                ticketLog.setLevelKBefore(value);
                                break;
                            case 4016:
                                ticketLog.setLevelKAfter(value);
                                break;
                            case 2472:
                                ticketLog.setTicketQuality(value);
                                break;
                            case 4235:
                                ticketLog.setClassifyReason(value);
                                break;
                        }
                    }
                }
            }
            ticketLogRepository.save(ticketLog);
        }
    }

    @Override
    public void backup() throws UnirestException {
        List<Long> listId = ticketRepository.findIdNotInTicketLog();
        log.info("[Backup Ticket] Size: " + listId.size());
        List<CustomFields> customFieldsList = this.customFieldRepository.findAll();
        for (Long id : listId) {
            this.saveTicketLog(id, customFieldsList);
        }
    }

    private HttpResponse<String> getTicketByUpdated(String updatedTime) throws UnirestException {
        return Unirest.get("https://api.caresoft.vn/NativeSmile/api/v1/tickets")
                .queryString("updated_since", updatedTime)
                .queryString("order_by", "updated_at")
                .queryString("order_type", "asc")
                .queryString("page", 1)
                .queryString("count", 500)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
    }

    private HttpResponse<String> getTicketById(long id) throws UnirestException {
        return Unirest.get("https://api.caresoft.vn/NativeSmile/api/v1/tickets/" + id)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
    }
}
