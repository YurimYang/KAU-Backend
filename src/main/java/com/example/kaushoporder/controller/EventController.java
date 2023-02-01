package com.example.kaushoporder.controller;


import com.example.kaushoporder.entity.Event;
import com.example.kaushoporder.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/v1/order/events")
    public ResponseEntity<Iterable<Event>> getEvents() {
        Iterable<Event> events = eventService.getEvents();

        return ResponseEntity.ok(events);
    }
}
