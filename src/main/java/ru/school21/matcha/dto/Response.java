package ru.school21.matcha.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private static final long serialVersionUID = 776095200796548861L;

    private final List<String> messages;
    private final boolean success;

    @JsonCreator
    public Response(@JsonProperty("messages") List<String> messages,
                    @JsonProperty("success") boolean success) {
        this.success = success;
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public boolean isSuccess() {
        return success;
    }
}
