package vn.edu.topica.crawler.caresoft.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "contact_list")
public class ContactList {

    @Id
    private Long id;

    private String email;

    private String username;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;


    public ContactList() {
    }
}
