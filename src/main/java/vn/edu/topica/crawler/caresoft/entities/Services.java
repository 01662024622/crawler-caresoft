package vn.edu.topica.crawler.caresoft.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Services")
public class Services {

    @Id
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_type")
    private String serviceType;

    public Services() {
    }
}
