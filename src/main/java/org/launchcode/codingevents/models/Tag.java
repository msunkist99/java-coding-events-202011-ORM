package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity     // model will be persisted in database
public class Tag extends AbstractEntity{

    @Size(max = 25, message = "Tag name is too long!")
    private String name;

    @ManyToMany(mappedBy="tags")
    private final List<Event> events = new ArrayList<>();

    // no arg constructor
    public Tag(){}

    // constructor
    public Tag(@Size(max = 25, message = "Tag name is too long!") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }
}
