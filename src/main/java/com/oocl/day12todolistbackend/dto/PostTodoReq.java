package com.oocl.day12todolistbackend.dto;

public class PostTodoReq {
    private String text;
    private boolean done;

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
