package com.snaykmob.noteify.dto;


public class ItemDTO implements ITableDTO {

    private long id;
    private long categoryId;
    private String text;
    private long completed;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public long getCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(long completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return text;
    }
}
