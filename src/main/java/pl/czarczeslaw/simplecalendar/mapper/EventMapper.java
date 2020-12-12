package pl.czarczeslaw.simplecalendar.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.czarczeslaw.simplecalendar.model.Event;
import pl.czarczeslaw.simplecalendar.model.dto.EventDto;
import pl.czarczeslaw.simplecalendar.model.dto.EventEditDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "created", ignore = true)
    Event saveEventByDto(EventDto dto);

    @Mapping(target = "id", source = "dto.editId")
    @Mapping(target = "created", ignore = true)
    Event editEventByDto(EventEditDto dto);

    @Mapping(target = "editId", source = "event.id")
    EventEditDto getEditDtoFromEvent(Event event);
}
