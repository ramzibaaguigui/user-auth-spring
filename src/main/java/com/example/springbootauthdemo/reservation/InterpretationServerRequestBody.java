package com.example.springbootauthdemo.reservation;

import org.springframework.lang.NonNull;

public class InterpretationServerRequestBody {

    private String param;

    public static InterpretationServerRequestBody create(@NonNull String param) {
        InterpretationServerRequestBody body = new InterpretationServerRequestBody();
        body.param = param;
        return body;
    }
}
