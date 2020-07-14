package vn.edu.topica.crawler.caresoft.services.iservice;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface CallService {

  void importData() throws UnirestException;

}
