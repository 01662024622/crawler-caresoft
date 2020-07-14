package vn.edu.topica.crawler.caresoft.services.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.crawler.caresoft.entities.*;
import vn.edu.topica.crawler.caresoft.repositories.ContactListRepository;
import vn.edu.topica.crawler.caresoft.repositories.ContactRepository;
import vn.edu.topica.crawler.caresoft.services.iservice.ContactsService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ContactsServiceImpl implements ContactsService {

    private ContactListRepository contactListRepository;
    private ContactRepository contactRepository;

    @Autowired
    public ContactsServiceImpl(ContactListRepository contactListRepository,
                               ContactRepository contactRepository) {
        this.contactListRepository = contactListRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public void importData() throws UnirestException {
        Object obj = contactListRepository.findLastUpdatedTime();
        String updatedTime = obj == null ? "2018-01-01T00:00:00Z" : obj.toString();
        log.info("Max updated time: " + updatedTime);
        HttpResponse<String> response = getContactByUpdatedTime(updatedTime);
        JSONObject res = new JSONObject(response.getBody());
        if (res.get("code").toString().toLowerCase().equals("ok")) {
            JSONArray contacts = res.getJSONArray("contacts");
            for (Object object : contacts) {
                JSONObject jsonObject = (JSONObject) object;
                ContactList contact = new ContactList();
                if (!jsonObject.isNull("id")) {
                    contact.setId(jsonObject.getLong("id"));
                }
                if (!jsonObject.isNull("phone_no")) {
                    contact.setPhoneNo(jsonObject.get("phone_no").toString());
                }
                if (!jsonObject.isNull("email")) {
                    contact.setEmail(jsonObject.get("email").toString());
                }
                if (!jsonObject.isNull("username")) {
                    contact.setUsername(jsonObject.get("username").toString());
                }
                if (!jsonObject.isNull("created_at")) {
                    contact.setCreatedAt(jsonObject.get("created_at").toString());
                }
                if (!jsonObject.isNull("updated_at")) {
                    contact.setUpdatedAt(jsonObject.get("updated_at").toString());
                }
                contactListRepository.save(contact);
                saveContact(contact.getId());
            }
        }
    }

    private void saveContact(long id) throws UnirestException {
        HttpResponse<String> resp = getContactById(id);
        JSONObject jsonRes = new JSONObject(resp.getBody());
        if (jsonRes.has("code") && jsonRes.get("code").toString().toLowerCase().equals("ok")) {
            JSONObject contact = jsonRes.getJSONObject("contact");
            Contact c = new Contact();
            if (contact.has("id") && !contact.isNull("id")) {
                c.setId(contact.getLong("id"));
            }
            if (contact.has("username") && !contact.isNull("username")) {
                c.setUsername(contact.get("username").toString());
            }
            if (contact.has("email") && !contact.isNull("email")) {
                c.setEmail(contact.get("email").toString());
            }
            if (contact.has("email2") && !contact.isNull("email2")) {
                c.setEmail2(contact.get("email2").toString());
            }
            if (contact.has("phone_no") && !contact.isNull("phone_no")) {
                c.setPhoneNo(contact.get("phone_no").toString());
            }
            if (contact.has("phone_no2") && !contact.isNull("phone_no2")) {
                c.setPhoneNo2(contact.get("phone_no2").toString());
            }
            if (contact.has("phone_no3") && !contact.isNull("phone_no3")) {
                c.setPhoneNo3(contact.get("phone_no3").toString());
            }
            if (contact.has("facebook") && !contact.isNull("facebook")) {
                c.setFacebook(contact.get("facebook").toString());
            }
            if (contact.has("gender") && !contact.isNull("gender")) {
                if (Integer.parseInt(contact.get("gender").toString()) == 0) {
                    c.setGender("Nam");
                } else if (Integer.parseInt(contact.get("gender").toString()) == 1) {
                    c.setGender("Nữ");
                } else if (Integer.parseInt(contact.get("gender").toString()) == 2) {
                    c.setGender("Khác");
                } else {
                    c.setGender("null");
                }
            }
            if (contact.has("organization_id") && !contact.isNull("organization_id")) {
                c.setOrganizationId(contact.get("organization_id").toString());
            }
            if (contact.has("created_at") && !contact.isNull("created_at")) {
                c.setCreatedAt(contact.get("created_at").toString());
            }
            if (contact.has("updated_at") && !contact.isNull("updated_at")) {
                c.setUpdatedAt(contact.get("updated_at").toString());
            }
            if (contact.has("organization") && !contact.isNull("organization")) {
                c.setOrganization(contact.get("organization").toString());
            }
            contactRepository.save(c);
        }
    }

    @Override
    public void backup() throws UnirestException {
        List<Long> listId = contactRepository.findIdNotInContacts();
        log.info("[Backup Contacts] Size: " + listId.size());
        for (Long id : listId) {
            this.saveContact(id);
        }
    }

    private HttpResponse<String> getContactByUpdatedTime(String updatedTime)
            throws UnirestException {
        return Unirest.get("https://api.caresoft.vn/NativeSmile/api/v1/contacts")
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

    private HttpResponse<String> getContactById(Long id)
            throws UnirestException {
        return Unirest.get("https://api.caresoft.vn/NativeSmile/api/v1/contacts/" + id)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer pmJl46MEbAeb2nU")
                .header("cache-control", "no-cache")
                .asString();
    }
}
