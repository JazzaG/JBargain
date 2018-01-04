package com.somethinglurks.jbargain.api.user;

public class AuthenticationResult {

    private Status status;
    private User user;

    public AuthenticationResult(Status status, User user) {
        this.status = status;
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public enum Status {
        SUCCESS,
        INCORRECT_CREDENTIALS,
        ERROR
    }

}
