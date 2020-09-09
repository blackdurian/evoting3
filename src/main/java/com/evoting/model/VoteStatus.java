package com.evoting.model;

public enum VoteStatus {
    VOTED ("You has Voted!"),
    NOT_VOTED("Please scan again!"),
    NOT_REGISTER("Please register your vote!");
    // declaring private variable for getting values
    private String message;

    // getter method
    public String getMessage()
    {
        return this.message;
    }
    VoteStatus(String message) {
        this.message = message;
    }
}
