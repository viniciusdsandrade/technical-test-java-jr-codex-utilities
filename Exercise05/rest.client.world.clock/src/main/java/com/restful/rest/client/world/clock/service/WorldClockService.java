package com.restful.rest.client.world.clock.service;

import com.restful.rest.client.world.clock.exception.WorldClockException;
import com.restful.rest.client.world.clock.model.WorldClockResponse;

public interface WorldClockService {

    /// Metodo que busca o horário atual no formato UTC.
    ///
    /// Este metodo realiza a comunicação com uma fonte externa de dados para recuperar a hora no formato UTC,
    /// encapsulando a resposta num objeto 'WorldClockResponse'.
    /// Implementações deste metodo devem lidar
    /// com possíveis falhas na comunicação com a API e garantir que a resposta seja válida.
    ///
    /// @return Uma instância de 'WorldClockResponse' contendo a hora atual no formato UTC ou 'null' em caso de falha.
    WorldClockResponse getCurrentUtcTime() throws WorldClockException;
}
