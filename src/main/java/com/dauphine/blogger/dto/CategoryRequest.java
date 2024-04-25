package com.dauphine.blogger.dto;

import java.util.UUID;

public class CategoryRequest {
    private String name;

    public CategoryRequest() {}
    public CategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
