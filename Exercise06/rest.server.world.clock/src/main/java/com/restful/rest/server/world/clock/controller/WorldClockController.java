package com.restful.rest.server.world.clock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController("WorldClockController")
public class WorldClockController {

    /// Retorna o horário UTC atual em formato JSON.
    ///
    /// Este metodo é acessado via uma requisição GET para a URL "/api/time/utc". Ele
    /// obtém o horário atual no fuso horário UTC e o formata usando o padrão ISO-8601.
    /// O horário é então retornado no formato JSON com o campo '"currentDateTime"'.
    ///
    /// Esse recurso é útil em aplicações onde é necessário obter a data e hora
    /// no padrão UTC para sincronização de eventos globais ou registro de logs.
    ///
    /// @return Um 'Map' contendo o horário UTC atual no formato '"currentDateTime"'.
    @GetMapping(value = "/api/time/utc", produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getCurrentUtcTime() {
        ZonedDateTime utcNow = now(UTC); // Obtém a data e hora atual no fuso horário UTC.
        String formattedUtcTime = utcNow.format(ISO_INSTANT); // Formata a data e hora obtida no padrão ISO-8601.
        Map<String, String> response = new HashMap<>(); // Cria um mapa para armazenar a resposta no formato JSON.
        response.put("currentDateTime", formattedUtcTime); // Adiciona a data e hora formatada ao mapa com a chave "currentDateTime".

        return response;
    }
}
