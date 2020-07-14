package vn.edu.topica.crawler.caresoft.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Agents")
public class Agents {
    @Id
    private Long id;
    @Column(name = "username")
    private String userName;
    private String email;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "agent_id")
    private String agentId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "role_id")
    private Long roleId;

    public Agents() {
    }
}
