package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.service.dto.EventDTO;

public class EventMapper {

    public static Event convertEventDTOtoEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setPublic(eventDTO.getPublic());
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setNotes(eventDTO.getNotes());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        return event;
    }

    public static EventDTO convertEventtoEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setPublic(event.isPublic());
        eventDTO.setName(event.getName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setNotes(event.getNotes());
        eventDTO.setStartDate(event.getStartDate());
        eventDTO.setEndDate(event.getEndDate());
        return eventDTO;
    }
}
