package webhook.com.repository;

import webhook.com.model.Webhook;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface WebhookRepository extends MongoRepository<Webhook, String> {
    
    List<Webhook> findByEventType(String eventType);
    
    List<Webhook> findByActive(boolean active);
    
    List<Webhook> findByUrlContaining(String urlFragment);
}