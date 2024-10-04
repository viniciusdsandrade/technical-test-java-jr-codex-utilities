package com.restful.rest.server.world.clock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class WorldClockController {

    @GetMapping(value = "/api/time/utc", produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getCurrentUtcTime() {
        ZonedDateTime utcNow = now(ZoneOffset.UTC);
        String formattedUtcTime = utcNow.format(ISO_INSTANT);

        Map<String, String> response = new HashMap<>();
        response.put("currentDateTime", formattedUtcTime);

        return response;
    }
}
