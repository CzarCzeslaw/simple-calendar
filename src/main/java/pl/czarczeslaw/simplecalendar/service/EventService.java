package pl.czarczeslaw.simplecalendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.czarczeslaw.simplecalendar.mapper.EventMapper;
import pl.czarczeslaw.simplecalendar.model.Event;
import pl.czarczeslaw.simplecalendar.model.dto.EventDto;
import pl.czarczeslaw.simplecalendar.model.dto.EventEditDto;
import pl.czarczeslaw.simplecalendar.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()){
            throw new EntityNotFoundException("Not found event with id: " + id);
        }
        return optionalEvent.get();
    }

    public List<Event> getListByName(String name) {
        if (name != null) {
            return eventRepository.findAllByName(name);
        } else return new LinkedList<>();
    }

    public boolean saveEvent(EventDto dto) {
        Event event = eventMapper.saveEventByDto(dto);
        eventRepository.save(event);
        return true;
    }

    public Event editEvent(EventEditDto dto) {
        if (eventRepository.existsById(dto.getEditId())) {
            Event event = eventMapper.editEventByDto(dto);
            eventRepository.save(event);
            return event;
        } else throw new EntityNotFoundException(" Not founded Event with id: " + dto.getEditId());
    }

    public boolean deleteEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            eventRepository.delete(optionalEvent.get());
            return true;
        } else {
            throw new EntityNotFoundException(" Not founded Event with id: " + id);
        }
    }

    public List<Event> getPast() {
        List<Event> eventList = eventRepository.findAll();
        int counter = 0;
        for (Event event : eventList) {
            if (event.getDate().isAfter(LocalDate.now())) {
                counter++;
                eventList.remove(counter);
            }
        }
        return eventList;
    }

    public List<Event> getToday() {
        List<Event> eventList = eventRepository.findAll();
        int counter = 0;
        for (Event event : eventList) {
            if (event.getDate().isEqual(LocalDate.now())) {
                counter++;
                eventList.remove(counter);
            }
        }
        return eventList;
    }
}
