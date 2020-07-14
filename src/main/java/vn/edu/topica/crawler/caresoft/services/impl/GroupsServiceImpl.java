package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.Groups;
import vn.edu.topica.crawler.caresoft.repositories.GroupsRepository;
import vn.edu.topica.crawler.caresoft.services.iservice.GroupsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Slf4j
public class GroupsServiceImpl implements GroupsService {

    private GroupsRepository groupsRepository;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public void importData() throws UnirestException {
        HttpResponse<String> response = getGroups();
        JSONObject res = new JSONObject(response.getBody());
        if (res.get("code").toString().toLowerCase().equals("ok")) {
            JSONArray contacts = res.getJSONArray("groups");
            for (Object object : contacts) {
                JSONObject jsonObject = (JSONObject) object;
                Groups groups = new Groups();
                if (!jsonObject.isNull("group_id")) {
                    groups.setGroupId(jsonObject.getLong("group_id"));
                }
                if (!jsonObject.isNull("group_name")) {
                    groups.setGroupName(jsonObject.getString("group_name"));
                }
                if (!jsonObject.isNull("created_at")) {
                    String createdAt = jsonObject.getString("created_at");
                    try {
                        groups.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdAt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                groupsRepository.save(groups);
            }
        }
    }

    private HttpResponse<String> getGroups()
            throws UnirestException {
        HttpResponse<String> response = Unirest.get(
                "https://api.caresoft.vn/NativeSmile/api/v1/groups")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
        return response;
    }
}
