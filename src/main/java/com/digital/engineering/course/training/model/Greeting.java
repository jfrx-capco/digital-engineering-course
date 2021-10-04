package com.digital.engineering.course.training.model;

import io.swagger.annotations.ApiModelProperty;

public class Greeting {

    @ApiModelProperty(notes = "A random generated ID")
    private long id;

    @ApiModelProperty(notes = "The greetings content")
    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
