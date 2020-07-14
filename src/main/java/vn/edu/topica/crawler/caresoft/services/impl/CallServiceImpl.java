package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.CallHistory;
import vn.edu.topica.crawler.caresoft.repositories.CallRepository;
import vn.edu.topica.crawler.caresoft.services.iservice.CallService;

@Service
@Slf4j
public class CallServiceImpl implements CallService {

    private CallRepository callRepository;

    @Autowired
    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public void importData() throws UnirestException {
        Object obj = callRepository.findLastStartTime();
        String startTimeText = obj == null ? "2018-01-01 00:00:00" : obj.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startTimeSince = sdf.format(date);
        log.info("Max start time: " + startTimeSince);
        HttpResponse<String> response = getCallsByStartTimeSince(startTimeSince);
        JSONObject res = new JSONObject(response.getBody());
        if (res.get("code").toString().toLowerCase().equals("ok")) {
            JSONArray tickets = res.getJSONArray("calls");
            List<CallHistory> callHistories = new ArrayList<>();
            for (Object object : tickets) {
                JSONObject jsonObject = (JSONObject) object;
                CallHistory call = new CallHistory();
                if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                    call.setId(jsonObject.getLong("id"));
                }
                if (jsonObject.has("customer_id") && !jsonObject.isNull("customer_id")) {
                    call.setCustomerId(jsonObject.getLong("customer_id"));
                }
                if (jsonObject.has("call_id") && !jsonObject.isNull("call_id")) {
                    call.setCallId(String.valueOf(jsonObject.get("call_id")));
                }
                if (jsonObject.has("path") && !jsonObject.isNull("path")) {
                    call.setPath(String.valueOf(jsonObject.get("path")));
                }
                if (jsonObject.has("path_download") && !jsonObject.isNull("path_download")) {
                    call.setPathDownload(String.valueOf(jsonObject.get("path_download")));
                }
                if (jsonObject.has("caller") && !jsonObject.isNull("caller")) {
                    call.setCaller(String.valueOf(jsonObject.get("caller")));
                }
                if (jsonObject.has("called") && !jsonObject.isNull("called")) {
                    call.setCalled(String.valueOf(jsonObject.get("called")));
                }
                if (jsonObject.has("user_id") && !jsonObject.isNull("user_id")) {
                    call.setUserId(String.valueOf(jsonObject.get("user_id")));
                }
                if (jsonObject.has("agent_id") && !jsonObject.isNull("agent_id")) {
                    call.setAgentId(String.valueOf(jsonObject.get("agent_id")));
                }
                if (jsonObject.has("group_id") && !jsonObject.isNull("group_id")) {
                    call.setGroupId(jsonObject.getLong("group_id"));
                }
                if (jsonObject.has("call_type") && !jsonObject.isNull("call_type")) {
                    call.setCallType(jsonObject.getLong("call_type"));
                }
                if (jsonObject.has("start_time") && !jsonObject.isNull("start_time")) {
                    call.setStartTime(jsonObject.getString("start_time"));
                }
                if (jsonObject.has("call_status") && !jsonObject.isNull("call_status")) {
                    call.setCallStatus(String.valueOf(jsonObject.get("call_status")));
                }
                if (jsonObject.has("end_time") && !jsonObject.isNull("end_time")) {
                    call.setEndTime(jsonObject.getString("end_time"));
                }
                if (jsonObject.has("wait_time") && !jsonObject.isNull("wait_time")) {
                    call.setWaitTime(String.valueOf(jsonObject.get("wait_time")));
                }
                if (jsonObject.has("hold_time") && !jsonObject.isNull("hold_time")) {
                    call.setHoldTime(String.valueOf(jsonObject.get("hold_time")));
                }
                if (jsonObject.has("talk_time") && !jsonObject.isNull("talk_time")) {
                    call.setTalkTime(String.valueOf(jsonObject.get("talk_time")));
                }
                if (jsonObject.has("end_status") && !jsonObject.isNull("end_status")) {
                    call.setEndStatus(String.valueOf(jsonObject.get("end_status")));
                }
                if (jsonObject.has("ticket_id") && !jsonObject.isNull("ticket_id")) {
                    call.setTicketId(String.valueOf(jsonObject.get("ticket_id")));
                }
                if (jsonObject.has("last_agent_id") && !jsonObject.isNull("last_agent_id")) {
                    call.setLastAgentId(String.valueOf(jsonObject.get("last_agent_id")));
                }
                if (jsonObject.has("last_user_id") && !jsonObject.isNull("last_user_id")) {
                    call.setLastUserId(Long.parseLong(jsonObject.get("last_user_id").toString()));
                }
                if (jsonObject.has("call_survey") && !jsonObject.isNull("call_survey")) {
                    call.setCallSurvey(String.valueOf(jsonObject.get("call_survey")));
                }
                if (jsonObject.has("call_survey_result") && !jsonObject.isNull("call_survey_result")) {
                    call.setCallSurveyResult(String.valueOf(jsonObject.get("call_survey_result")));
                }
                callHistories.add(call);
            }
            if (callHistories.size() > 0) {
                callRepository.saveAll(callHistories);
            }
        }
    }

    private HttpResponse<String> getCallsByStartTimeSince(String startTimeSince)
            throws UnirestException {
        //      GET:  https://api.caresoft.vn/NativeSmile/api/v1/calls?start_time_since=1990-01-01T00:00:00Z&order_by=id&order_type=asc&count=100
        //      Nếu không truyền các tham số này thì mặc định: page=1, count=50, order_by=start_time_since, order_type=desc, giá trị count tối đa là: 500.
        HttpResponse<String> response = Unirest.get(
                "https://api.caresoft.vn/NativeSmile/api/v1/calls")
                .queryString("start_time_since", startTimeSince)
                .queryString("order_by", "start_time")
                .queryString("order_type", "asc")
                .queryString("page", 1)
                .queryString("count", 500)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
        return response;
    }
}
