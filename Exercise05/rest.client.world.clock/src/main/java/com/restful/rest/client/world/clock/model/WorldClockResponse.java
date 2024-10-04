package com.restful.rest.client.world.clock.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/// 'WorldClockResponse' é um record que representa a resposta da API World Clock.
///
/// Este record modela a estrutura da resposta JSON recebida da API de hora mundial.
/// Ele utiliza a anotação '@JsonProperty' para mapear o campo JSON "utc_datetime" para
/// a propriedade 'utcDateTime'.
///
/// O uso de records no Java fornece uma maneira concisa de declarar classes imutáveis
/// com comportamento de getter implícito, tornando o código mais enxuto e de fácil manutenção.
///
/// @param utcDateTime A data e hora no formato UTC recebida da API, no formato de string.
public record WorldClockResponse(
        @JsonProperty("utc_datetime") String utcDateTime
) {
}
