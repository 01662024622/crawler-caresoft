package vn.edu.topica.crawler.caresoft.services.iservice;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface AgentsService {

  void importData() throws UnirestException;

}
