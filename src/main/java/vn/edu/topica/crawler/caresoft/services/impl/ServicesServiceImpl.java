package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.Services;
import vn.edu.topica.crawler.caresoft.repositories.ServicesRepository;
import vn.edu.topica.crawler.caresoft.services.iservice.ServicesService;

@Service
@Slf4j
public class ServicesServiceImpl implements ServicesService {

    private ServicesRepository servicesRepository;

    @Autowired
    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Override
    public void importData() throws UnirestException {
        HttpResponse<String> response = getServices();
        JSONObject res = new JSONObject(response.getBody());
        if (res.get("code").toString().toLowerCase().equals("ok")) {
            JSONArray contacts = res.getJSONArray("services");
            for (Object object : contacts) {
                JSONObject jsonObject = (JSONObject) object;
                Services services = new Services();
                if (!jsonObject.isNull("service_id")) {
                    services.setServiceId(jsonObject.getLong("service_id"));
                }
                if (!jsonObject.isNull("service_name")) {
                    services.setServiceName(jsonObject.getString("service_name"));
                }
                if (!jsonObject.isNull("service_type")) {
                    services.setServiceType(jsonObject.getString("service_type"));
                }
                servicesRepository.save(services);
            }
        }
    }

    private HttpResponse<String> getServices()
            throws UnirestException {
        HttpResponse<String> response = Unirest.get(
                "https://api.caresoft.vn/NativeSmile/api/v1/services")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
        return response;
    }
}
