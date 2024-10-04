package com.restful.rest.client.world.clock.exception;

/// 'WorldClockHttpException' representa erros específicos de comunicação HTTP ao tentar acessar a API World Clock.
///
/// Esta exceção é usada para capturar problemas relacionados as requisições HTTP, como falhas de rede, tempo de resposta
/// da API ou erros de status HTTP. Permite que o sistema diferencie entre erros gerais e erros de comunicação com a API.
///
/// @implNote Pode ser usada para encapsular `HttpClientErrorException` ou outros problemas relacionados à camada HTTP.
public class WorldClockHttpException extends WorldClockException {

    /// Constrói uma nova exceção 'WorldClockHttpException' com a mensagem fornecida.
    ///
    /// @param message A mensagem descritiva do erro de comunicação HTTP.
    public WorldClockHttpException(String message) {
        super(message);
    }

    /// Constrói uma nova exceção 'WorldClockHttpException' com a mensagem e a causa fornecidas.
    ///
    /// @param message A mensagem descritiva do erro de comunicação HTTP.
    /// @param cause A causa original do erro (uma exceção de rede ou HTTP).
    public WorldClockHttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
