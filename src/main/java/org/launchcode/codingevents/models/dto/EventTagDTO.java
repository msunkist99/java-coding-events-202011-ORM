package org.launchcode.codingevents.models.dto;

import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.Tag;

import javax.validation.constraints.NotNull;

// this is not used to persist data in the database
// so no @Entity annotation
public class EventTagDTO {
    @NotNull
    private Event event;

    @NotNull
    private Tag tag;

    // no argument constructor
    public EventTagDTO(){}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
