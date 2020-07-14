package vn.edu.topica.crawler.caresoft.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CustomFieldMappingId implements Serializable {
    @Column(name = "ticket_id")
    private long ticketId;

    @Column(name = "custom_field_id")
    private long customFieldId;

    public CustomFieldMappingId() {
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(long customFieldId) {
        this.customFieldId = customFieldId;
    }
}
