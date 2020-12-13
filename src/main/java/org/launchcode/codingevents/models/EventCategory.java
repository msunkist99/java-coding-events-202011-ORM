package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class EventCategory {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Category Name is required")
    @Size(min = 3, max = 50, message = "Category Name must be between 3 and 50 characters")
    private String name;

    // noArg constructor
    public EventCategory(){}

    public EventCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
