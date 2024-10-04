package com.restful.todo.list.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

/**
 * Classe responsável por tratar exceções de forma global na aplicação, proporcionando
 * uma resposta uniforme e amigável ao cliente em caso de erros.
 * <p>
 * Esta classe utiliza as anotações do Spring para interceptar exceções lançadas
 * nos controladores REST e retornar mensagens de erro apropriadas ao cliente.
 *
 * @see RestControllerAdvice
 * @see ExceptionHandler
 */
@RestControllerAdvice(basePackages = "com.restful.todo.list.controller")
@Schema(description = "Classe responsável por tratar exceções globalmente na aplicação.")
public class GlobalExceptionHandler {


    /**
     * Manipula exceções genéricas do tipo {@link Exception}, que representam erros inesperados
     * que podem ocorrer durante o processamento de uma requisição.
     * <p>
     * Este metodo atua como um "catch-all" para exceções não tratadas especificamente por outros
     * metodos de manipulação de exceções. Ele captura qualquer exceção do tipo Exception e retorna
     * uma resposta com status HTTP 500 (Internal Server Error), indicando que houve um erro interno
     * no servidor que impediu o processamento da requisição.
     * </p>
     * <p>
     * O metodo encapsula os detalhes do erro em um objeto {@link ErrorDetails} e retorna uma resposta
     * com a mensagem "Erro interno no servidor" e o status HTTP 500. Essa resposta informa ao
     * cliente que houve um problema no servidor, mas não expõe detalhes específicos sobre a causa
     * do erro, por questões de segurança e para evitar vazamento de informações sensíveis.
     * </p>
     *
     * @param exception  A exceção genérica que representa o erro interno no servidor.
     * @param webRequest O objeto {@link WebRequest} que fornece informações adicionais sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo uma lista com os detalhes do erro encapsulados em {@link ErrorDetails}
     * e o status HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @Schema(description = "Manipula exceções genéricas, representando erros inesperados durante o processamento da requisição.")
    public ResponseEntity<List<ErrorDetails>> handleGlobalException(Exception exception,
                                                                    WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(List.of(errorDetails), INTERNAL_SERVER_ERROR);
    }

    /**
     * Manipula exceções que indicam que uma funcionalidade não está implementada,
     * correspondente ao código de status HTTP 501 (Not Implemented).
     * <p>
     * Este metodo captura exceções do tipo {@link UnsupportedOperationException}, que
     * geralmente são lançadas quando um metodo ou recurso ainda não foi implementado
     * na aplicação.
     * </p>
     * <p>
     * O metodo encapsula os detalhes do erro em um objeto {@link ErrorDetails} e retorna uma resposta
     * com a mensagem "Funcionalidade não implementada" e o status HTTP 501. Essa resposta informa ao
     * cliente que a funcionalidade solicitada ainda não está disponível.
     * </p>
     *
     * @param exception  A exceção que representa a funcionalidade não implementada.
     * @param webRequest O objeto {@link WebRequest} que fornece informações adicionais sobre a requisição que causou a exceção.
     * @return Uma {@link ResponseEntity} contendo uma lista com os detalhes do erro encapsulados em {@link ErrorDetails}
     * e o status HTTP 501 (Not Implemented).
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @Schema(description = "Manipula exceções que indicam que uma funcionalidade não está implementada.")
    public ResponseEntity<List<ErrorDetails>> handleNotImplementedException(UnsupportedOperationException exception,
                                                                            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "NOT_IMPLEMENTED"
        );

        return new ResponseEntity<>(List.of(errorDetails), NOT_IMPLEMENTED);
    }
}
