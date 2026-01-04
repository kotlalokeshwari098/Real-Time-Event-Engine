package org.example.domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Event{
    private final UUID eventId;
    private final LocalDateTime timestamp;
    private final EventType type;
    private final EventState state;

    //business logic,persistence
    private final Map<String,String> payload;


    private Event(Builder build) {
        this.eventId = build.eventId;
        this.timestamp = build.timestamp;
        this.type = build.type;
        this.state = build.state;
        this.payload = build.payload;
    }

    public EventType getType() {
        return type;
    }

    public EventState getState() {
        return state;
    }

    public Map<String, String> getPayload() {
        return payload;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event that = (Event) o;
        return Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    public UUID getId() {
        return eventId;
    }

    public static class Builder{
        private UUID eventId;
        private LocalDateTime timestamp;
        private EventType type;
        private EventState state;

        //business logic,persistence
        private Map<String,String> payload;

        public Builder uuid(UUID eventId){
            this.eventId=eventId;
            return this;
        }

        public Builder localdatetime(LocalDateTime timestamp){
            this.timestamp=timestamp;
            return this;

        }
        public Builder eventtype(EventType type){
            this.type=type;
            return this;
        }

        public Builder eventstate(EventState state){
            this.state=state;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }
}
