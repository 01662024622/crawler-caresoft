package vn.edu.topica.crawler.caresoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ServiceContext {

  private final Environment env;

  @Autowired
  public ServiceContext(Environment env) {
    this.env = env;
  }

  public <T> T getConfig(String key, Class<T> type) {
    return env.getProperty(key, type);
  }
}
