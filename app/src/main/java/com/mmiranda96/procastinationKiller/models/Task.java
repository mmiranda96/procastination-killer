package com.mmiranda96.procastinationKiller.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Task {
    private int id;
    private String title, description;
    private Date due;
    private ArrayList<String> subtasks;

    public Task(String title, String description, Date due, String... subtasks) {
        this.title = title;
        this.description = description;
        this.due = due;
        this.subtasks = new ArrayList<>();
        this.subtasks.addAll(Arrays.asList(subtasks));
    }


    public Task(int id, String title, String description, Date due, String... subtasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.due = due;
        this.subtasks = new ArrayList<>();
        this.subtasks.addAll(Arrays.asList(subtasks));
    }

    public void addSubtask(String subtask) {
        this.subtasks.add(subtask);
    }

    @Override
    public String toString() {
        // TODO: make better
        String result = "\n{";
        result += "\tID: " + this.id + ",\n";
        result += "\tTitle: " + this.title + ",\n";
        result += "\tDescription: " + this.description + ",\n";
        result += "\tDue: " + this.due.toString() + ",\n";
        result += "\tSubtasks: " + this.subtasks.toString() + "\n";
        result += "}";
        return result;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDue() {
        return due;
    }

    public ArrayList<String> getSubtasks() {
        return subtasks;
    }
}
