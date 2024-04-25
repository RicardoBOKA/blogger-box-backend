package com.dauphine.blogger.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {
    @Id
    private UUID id;
    private String name;

    public Category() {}
    public Category(UUID uuid, String name) {
        this.id = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return id;
    }

    public void setUuid(UUID uuid) {
        this.id = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
