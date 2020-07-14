package vn.edu.topica.crawler.caresoft.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CustomFieldMapping")
public class CustomFieldMapping {
    @EmbeddedId
    private CustomFieldMappingId id;

    @Column(name = "custom_field_label")
    private String customFieldLabel;

    @Column(name = "custom_field_value")
    private String customFieldValue;
}
