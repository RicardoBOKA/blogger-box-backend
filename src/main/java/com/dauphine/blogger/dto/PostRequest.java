package com.dauphine.blogger.dto;

import com.fasterxml.jackson.annotation.JsonTypeId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class PostRequest {

    private String title;
    private String content;
    private UUID categoryId;


    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
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
}
