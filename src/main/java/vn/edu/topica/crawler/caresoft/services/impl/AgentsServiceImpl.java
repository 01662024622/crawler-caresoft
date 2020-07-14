package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.Agents;
import vn.edu.topica.crawler.caresoft.repositories.AgentsRepository;
import vn.edu.topica.crawler.caresoft.services.iservice.AgentsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Slf4j
public class AgentsServiceImpl implements AgentsService {

    private AgentsRepository agentsRepository;

    @Autowired
    public AgentsServiceImpl(AgentsRepository agentsRepository) {
        this.agentsRepository = agentsRepository;
    }

    @Override
    public void importData() throws UnirestException {
        HttpResponse<String> response = getAgents();
        JSONObject res = new JSONObject(response.getBody());
        if (res.get("code").toString().toLowerCase().equals("ok")) {
            JSONArray contacts = res.getJSONArray("agents");
            for (Object object : contacts) {
                JSONObject jsonObject = (JSONObject) object;
                Agents agents = new Agents();
                if (!jsonObject.isNull("id")) {
                    agents.setId(jsonObject.getLong("id"));
                }
                if (!jsonObject.isNull("username")) {
                    agents.setUserName(jsonObject.getString("username"));
                }
                if (!jsonObject.isNull("email")) {
                    agents.setEmail(jsonObject.getString("email"));
                }
                if (!jsonObject.isNull("phone_no")) {
                    agents.setPhoneNo(jsonObject.getString("phone_no"));
                }
                if (!jsonObject.isNull("agent_id")) {
                    agents.setAgentId(jsonObject.getString("agent_id"));
                }
                if (!jsonObject.isNull("created_at")) {
                    String createdDate = jsonObject.getString("created_at");
                    try {
                        agents.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!jsonObject.isNull("updated_at")) {
                    String updatedAt = jsonObject.getString("updated_at");
                    try {
                        agents.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updatedAt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!jsonObject.isNull("group_id")) {
                    agents.setGroupId(jsonObject.getLong("group_id"));
                }
                if (!jsonObject.isNull("group_name")) {
                    agents.setGroupName(jsonObject.getString("group_name"));
                }
                if (!jsonObject.isNull("role_id")) {
                    agents.setRoleId(jsonObject.getLong("role_id"));
                }
                agentsRepository.save(agents);
            }
        }
    }

    private HttpResponse<String> getAgents()
            throws UnirestException {
        HttpResponse<String> response = Unirest.get(
                "https://api.caresoft.vn/NativeSmile/api/v1/agents")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
        return response;
    }
}
