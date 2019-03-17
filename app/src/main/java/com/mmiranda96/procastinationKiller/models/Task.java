package com.mmiranda96.procastinationKiller.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Task implements Serializable {
    private int id;
    private String title, description;
    private Date due;
    private ArrayList<String> subtasks;

    public Task(String title, String description, Date due, ArrayList<String> subtasks) {
        this.title = title;
        this.description = description;
        this.due = due;
        this.subtasks = new ArrayList<>();
        this.subtasks.addAll(subtasks);
    }

    public Task(JSONObject jsonTask){
        try {
            this.id = jsonTask.getInt("id");
            this.title = jsonTask.getString("title");
            this.description = jsonTask.optString("description");
            long epoch = jsonTask.getLong("due");
            this.due = new Date(epoch);
            ArrayList<String> subtasks = new ArrayList<String>();
            JSONArray subtasksJSON = jsonTask.getJSONArray("subtasks");
            for(int i = 0; i < subtasksJSON.length(); i++){
                subtasks.add(subtasksJSON.getString(i));
            }
            this.subtasks = subtasks;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Task(int id, String title, String description, Date due) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.due = due;
        this.subtasks = new ArrayList<>();
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
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDue() {
        return this.due;
    }

    public ArrayList<String> getSubtasks() {
        return new ArrayList<>(this.subtasks);
    }
}
