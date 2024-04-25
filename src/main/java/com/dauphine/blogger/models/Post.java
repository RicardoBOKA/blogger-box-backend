package com.dauphine.blogger.models;

import com.fasterxml.jackson.annotation.JsonTypeId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Post {
    private UUID uuid;
    private String title;
    private String content;
    private LocalDateTime crated_date;

    private Category category;

    public Post() {}

    public Post(UUID uuid, String title, String content, LocalDateTime crated_date, Category category) {
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.crated_date = crated_date;
        this.category = category;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCrated_date() {
        return crated_date;
    }

    public void setCrated_date(LocalDateTime crated_date) {
        this.crated_date = crated_date;
    }
}
