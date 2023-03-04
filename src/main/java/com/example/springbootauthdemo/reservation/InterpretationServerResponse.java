package com.example.springbootauthdemo.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InterpretationServerResponse {
    @JsonProperty(value = "technical")
    private String technical;

    @JsonProperty(value = "non technical")
    private String nonTechnical;
}
