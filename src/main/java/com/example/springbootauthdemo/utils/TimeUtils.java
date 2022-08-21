package com.example.springbootauthdemo.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class TimeUtils {
    public Date now() {
        return Date.from(Instant.now());
    }

    public Date calculateTokenExpiryDate(Date issuedAt) {
        return Date.from(Instant.ofEpochMilli(issuedAt.getTime())
                .plusSeconds(Constants.TOKEN_EXPIRATION_TIME_ONE_DAY_SECONDS));
    }
}
