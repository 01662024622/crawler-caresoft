package vn.edu.topica.crawler.caresoft.services.iservice;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface TicketService {

  void importData() throws UnirestException;

  void backup() throws UnirestException;
}
