package com.crud.library.domain;

public enum Status {
    AVAILABLE("AVAILABLE"), BORROWED("BORROWED"), LOST("LOST");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
