package vn.edu.topica.crawler.caresoft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Contacts")
public class Contact {

    @Id
    private Long id;

    private String email;

    private String email2;

    private String username;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "phone_no2")
    private String phoneNo2;

    @Column(name = "phone_no3")
    private String phoneNo3;

    private String facebook;

    private String gender;

    private String organization;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    public Contact() {
    }
}
