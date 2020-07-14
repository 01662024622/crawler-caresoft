package vn.edu.topica.crawler.caresoft.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Groups")
public class Groups {

    @Id
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Groups() {
    }
}
