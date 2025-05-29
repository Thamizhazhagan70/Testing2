package webhook.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "webhooks") // MongoDB collection name
public class Webhook {
    
    @Id
    private String id;
    
    private String url;
    
    private String eventType;
    
    private String secretKey;
    
    private boolean active = true;
    
    private Date createdAt = new Date();
    
    private Date updatedAt;
    
    // Custom constructor without id and dates
    public Webhook(String url, String eventType, String secretKey, boolean active) {
        this.url = url;
        this.eventType = eventType;
        this.secretKey = secretKey;
        this.active = active;
    }
}