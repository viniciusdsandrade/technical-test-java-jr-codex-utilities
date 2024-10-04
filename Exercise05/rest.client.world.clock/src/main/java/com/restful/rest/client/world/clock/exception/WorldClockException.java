package com.restful.rest.client.world.clock.exception;

/// 'WorldClockException' é uma exceção personalizada que representa erros ocorridos ao tentar
/// obter o horário UTC da API World Clock.
///
/// Essa exceção pode ser utilizada para encapsular erros de comunicação HTTP, falhas de parsing,
/// ou quaisquer outras exceções relacionadas ao serviço de horário. O objetivo é centralizar o
/// tratamento de erros, oferecendo uma maneira mais clara e específica de lidar com problemas
/// na integração com a API.
///
/// @implNote É uma exceção checked, o que significa que deve ser tratada ou propagada explicitamente.
public class WorldClockException extends Exception {

    /// Constrói uma nova exceção 'WorldClockException' com a mensagem fornecida.
    ///
    /// @param message A mensagem descritiva do erro ocorrido.
    public WorldClockException(String message) {
        super(message);
    }

    /// Constrói uma nova exceção 'WorldClockException' com a mensagem e a causa fornecidas.
    ///
    /// @param message A mensagem descritiva do erro ocorrido.
    /// @param cause A causa original do erro (uma exceção que tenha sido capturada).
    public WorldClockException(String message, Throwable cause) {
        super(message, cause);
    }
}
