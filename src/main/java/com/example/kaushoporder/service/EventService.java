package com.example.kaushoporder.service;

import com.example.kaushoporder.entity.Event;
import com.example.kaushoporder.model.TransactionPhase;
import com.example.kaushoporder.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Optional<Event> getEvent(String uuid) {
        return eventRepository.findByUuid(uuid);
    }

    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(String uuid, Long orderId) {
        Event newEvent = new Event();
        newEvent.setUuid(uuid);
        newEvent.setOrderId(orderId);

        return eventRepository.save(newEvent);
    }

    public void updateEventStatus(String uuid, TransactionPhase phase) {
        eventRepository.findByUuid(uuid).ifPresent(event ->{
            event.setPhase(phase);
            eventRepository.save(event);
        });
    }
}
