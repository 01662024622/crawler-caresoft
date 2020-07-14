package vn.edu.topica.crawler.caresoft.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "CustomFields")
public class CustomFields {
    @Id
    private long id;

    private String label;

    private String type;

    @Column(name = "custom_field_id")
    private long customFieldId;

    @Column(name = "custom_field_label")
    private String customFieldLabel;
}
