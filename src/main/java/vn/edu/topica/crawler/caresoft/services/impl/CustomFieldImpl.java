package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.CustomFields;
import vn.edu.topica.crawler.caresoft.repositories.CustomFieldRepository;
import vn.edu.topica.crawler.caresoft.services.iservice.CustomFieldService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomFieldImpl implements CustomFieldService {

    private CustomFieldRepository customFieldRepository;

    @Autowired
    public CustomFieldImpl(CustomFieldRepository customFieldRepository) {
        this.customFieldRepository = customFieldRepository;
    }

    @Override
    public void importData() throws UnirestException {
        HttpResponse<String> resContact = getCustomFieldsContact();
        HttpResponse<String> resTicket = getCustomFieldsTicket();
        List<CustomFields> customFieldsList = new ArrayList<>();
        this.saveData(resContact, customFieldsList);
        this.saveData(resTicket, customFieldsList);
        if (customFieldsList.size() > 0) {
            customFieldRepository.saveAll(customFieldsList);
        }
    }

    private void saveData(HttpResponse<String> response, List<CustomFields> list) {
        JSONObject object = new JSONObject(response.getBody());
        if (!object.isNull("code") && object.get("code").toString().toLowerCase().equals("ok")) {
            JSONArray array = object.getJSONArray("custom_fields");
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                long customFieldId = jsonObject.getLong("custom_field_id");
                String customFieldLabel = jsonObject.getString("custom_field_lable");
                String type = jsonObject.getString("type");
                JSONArray values = jsonObject.getJSONArray("values");
                for (Object value : values) {
                    JSONObject v = (JSONObject) value;
                    long id = v.getLong("id");
                    String label = v.getString("lable");
                    CustomFields c = new CustomFields();
                    c.setId(id);
                    c.setCustomFieldId(customFieldId);
                    c.setCustomFieldLabel(customFieldLabel);
                    c.setLabel(label);
                    c.setType(type);
                    list.add(c);
                }
            }
        }
    }

    private HttpResponse<String> getCustomFieldsTicket()
            throws UnirestException {
        HttpResponse<String> response = Unirest.get(
                "https://api.caresoft.vn/NativeSmile/api/v1/tickets/custom_fields")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
        return response;
    }

    private HttpResponse<String> getCustomFieldsContact()
            throws UnirestException {
        HttpResponse<String> response = Unirest.get(
                "https://api.caresoft.vn/NativeSmile/api/v1/contacts/custom_fields")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
        return response;
    }
}
