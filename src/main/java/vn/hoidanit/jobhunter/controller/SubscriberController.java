package vn.hoidanit.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.service.SubscriberService;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;

    }

    @PostMapping("/subscribers")
    public ResponseEntity<Subscriber> createSubscribe(@RequestBody Subscriber subscriber) {
        return ResponseEntity.ok(this.subscriberService.handleCreate(subscriber));
    }

    @PutMapping("/subscribers")
    public ResponseEntity<Subscriber> updateSubscribe(@RequestBody Subscriber subscriber) {
        return ResponseEntity.ok(this.subscriberService.handleUpdate(subscriber));
    }

}
