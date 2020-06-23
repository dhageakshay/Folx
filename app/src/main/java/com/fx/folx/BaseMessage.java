package com.fx.folx;

public class BaseMessage {
    private String message;
    private User sender;
    private long createdAt;

    public BaseMessage(String message, User user, long createdAt){
        this.message = message;
        this.sender = user;
        this.createdAt = createdAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public User getSender() {
        return sender;
    }

}
