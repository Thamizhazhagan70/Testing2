package webhook.com.controller;

import webhook.com.model.Webhook;
import webhook.com.repository.WebhookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/webhooks")
@Slf4j
public class WebhookController {

    @Autowired
    private WebhookRepository webhookRepository;

    @GetMapping
    public ResponseEntity<List<Webhook>> getAllWebhooks() {
        log.info("Fetching all webhooks");
        return ResponseEntity.ok(webhookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Webhook> getWebhookById(@PathVariable String id) {
        Optional<Webhook> webhook = webhookRepository.findById(id);
        return webhook.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Webhook> createWebhook(@RequestBody Webhook webhook) {
        log.info("Creating new webhook: {}", webhook);
        Webhook savedWebhook = webhookRepository.save(webhook);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWebhook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Webhook> updateWebhook(@PathVariable String id, 
                                                @RequestBody Webhook webhookDetails) {
        return webhookRepository.findById(id)
                .map(existingWebhook -> {
                    existingWebhook.setUrl(webhookDetails.getUrl());
                    existingWebhook.setEventType(webhookDetails.getEventType());
                    existingWebhook.setSecretKey(webhookDetails.getSecretKey());
                    existingWebhook.setActive(webhookDetails.isActive());
                    existingWebhook.setUpdatedAt(new Date());
                    
                    Webhook updatedWebhook = webhookRepository.save(existingWebhook);
                    return ResponseEntity.ok(updatedWebhook);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebhook(@PathVariable String id) {
        if (webhookRepository.existsById(id)) {
            log.info("Deleting webhook with id: {}", id);
            webhookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/hello")
    public String sayHello() {
        return "Webhook API is up and running!";
    }
    @GetMapping("/event/{eventType}")
    public ResponseEntity<List<Webhook>> getWebhooksByEventType(@PathVariable String eventType) {
        log.info("Fetching webhooks for event type: {}", eventType);
        return ResponseEntity.ok(webhookRepository.findByEventType(eventType));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Webhook>> getActiveWebhooks() {
        log.info("Fetching all active webhooks");
        return ResponseEntity.ok(webhookRepository.findByActive(true));
    }
}