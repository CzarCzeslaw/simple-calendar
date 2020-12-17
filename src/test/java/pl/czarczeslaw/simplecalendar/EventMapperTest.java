package pl.czarczeslaw.simplecalendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.czarczeslaw.simplecalendar.mapper.EventMapper;
import pl.czarczeslaw.simplecalendar.model.Event;
import pl.czarczeslaw.simplecalendar.model.Priority;
import pl.czarczeslaw.simplecalendar.model.dto.EventDto;
import pl.czarczeslaw.simplecalendar.model.dto.EventEditDto;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EventMapperTest {

    //TODO: 'saveEventByDto' & 'editEventByDto'

    @Autowired
    EventMapper eventMapper;

    @Test
    void getEditDtoFromEventTest() {
        Event event = Event.builder().id(1L).name("EventName").created(LocalDate.of(2000, 12, 26))
                .date(LocalDate.of(2020, 06, 06)).time(LocalTime.MIDNIGHT)
                .priority(Priority.HIGH).build();

        EventEditDto dto = EventMapper.INSTANCE.getEditDtoFromEvent(event);

        assertThat(dto.getEditId()).isEqualTo(event.getId());
        assertThat(dto.getDate()).isEqualTo(event.getDate());
        assertThat(dto.getDescription()).isEqualTo(event.getDescription());
        assertThat(dto.getName()).isEqualTo(event.getName());
        assertThat(dto.getPriority()).isEqualTo(event.getPriority());
        assertThat(dto.getTime()).isEqualTo(event.getTime());
    }

    @Test
    void getSaveEventByDtoTest() {
        EventDto dto = EventDto.builder().name("EventName").date(LocalDate.of(2020, 06, 06))
                .time(LocalTime.MIDNIGHT).priority(Priority.HIGH).build();

        Event event = EventMapper.INSTANCE.saveEventByDto(dto);

        assertThat(dto.getDate()).isEqualTo(event.getDate());
        assertThat(dto.getDescription()).isEqualTo(event.getDescription());
        assertThat(dto.getName()).isEqualTo(event.getName());
        assertThat(dto.getPriority()).isEqualTo(event.getPriority());
        assertThat(dto.getTime()).isEqualTo(event.getTime());
    }

    @Test
    void getEditEventByDtoTest() {
        EventEditDto dto = EventEditDto.builder().name("EventName").date(LocalDate.of(2020, 06, 06))
                .time(LocalTime.MIDNIGHT).priority(Priority.HIGH).build();

        Event event = EventMapper.INSTANCE.editEventByDto(dto);

        assertThat(dto.getEditId()).isEqualTo(event.getId());
        assertThat(dto.getDate()).isEqualTo(event.getDate());
        assertThat(dto.getDescription()).isEqualTo(event.getDescription());
        assertThat(dto.getName()).isEqualTo(event.getName());
        assertThat(dto.getPriority()).isEqualTo(event.getPriority());
        assertThat(dto.getTime()).isEqualTo(event.getTime());
    }
}
