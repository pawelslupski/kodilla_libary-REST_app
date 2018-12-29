package com.crud.library.domain;

public enum Status {
    AVAILABLE("Available"), BORROWED("Borrowed"), LOST("Lost"), TEST("Test");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
