package com.dauphine.blogger.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id")
    private UUID uuid;
    private String title;
    private String content;
    private LocalDateTime created_date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Post() {}

    public Post(UUID uuid, String title, String content, LocalDateTime crated_date, Category category) {
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.created_date = crated_date;
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
        return created_date;
    }

    public void setCreated_date(LocalDateTime crated_date) {
        this.created_date = crated_date;
    }
}
