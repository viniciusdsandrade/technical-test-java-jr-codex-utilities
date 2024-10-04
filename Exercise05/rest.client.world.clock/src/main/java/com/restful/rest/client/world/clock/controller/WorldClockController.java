package com.restful.rest.client.world.clock.controller;

import com.restful.rest.client.world.clock.exception.WorldClockException;
import com.restful.rest.client.world.clock.model.WorldClockResponse;
import com.restful.rest.client.world.clock.service.WorldClockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/// Controlador da aplicação responsável por lidar com as requisições relacionadas ao horário UTC e horário local.
///
/// Este controlador expõe dois endpoints:
/// - Um para exibir o horário formatado na página inicial usando Thymeleaf.
/// - Outro para fornecer o horário no formato JSON via API REST.
///
/// A classe utiliza um serviço 'WorldClockService' para buscar o horário UTC a partir de uma API externa.
@Controller
public class WorldClockController {

    private final WorldClockService worldClockService;

    /// Construtor da classe 'WorldClockController'.
    ///
    /// O construtor recebe uma instância do serviço 'WorldClockService', que é injetada automaticamente pelo
    /// framework Spring. Este serviço é utilizado para buscar o horário UTC da API externa.
    ///
    /// @param worldClockService O serviço responsável por realizar a requisição à API de horário.
    public WorldClockController(WorldClockService worldClockService) {
        this.worldClockService = worldClockService;
    }

    /// Metodo responsável por lidar com requisições GET na raiz ("/") e exibir o horário UTC e o horário local
    /// no template Thymeleaf 'time.html'.
    ///
    /// Este metodo busca o horário UTC usando o 'WorldClockService', realiza o parsing da data/hora e converte
    /// para o horário local conforme o fuso horário do sistema. Em caso de erro, uma mensagem de erro é
    /// adicionada ao modelo.
    ///
    /// @param model O modelo que será utilizado pelo template Thymeleaf para exibir as informações.
    /// @return O nome do template Thymeleaf a ser renderizado.
    @GetMapping("/")
    public String showTime(Model model) {
        try {
            WorldClockResponse response = worldClockService.getCurrentUtcTime();

            if (response != null && response.utcDateTime() != null) {
                // Parse da data/hora UTC
                ZonedDateTime utcDateTime = ZonedDateTime.parse(response.utcDateTime());

                // Conversão para o horário local
                ZonedDateTime localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault());

                // Formatação das datas em UTC e local
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

                String formattedUtcTime = utcDateTime.format(formatter);
                String formattedLocalTime = localDateTime.format(formatter);

                // Adiciona os horários formatados ao modelo
                model.addAttribute("utcTime", formattedUtcTime);
                model.addAttribute("localTime", formattedLocalTime);
            } else {
                // Caso a resposta seja nula, adiciona uma mensagem de erro ao modelo
                model.addAttribute("error", "Não foi possível obter o horário no momento.");
            }
        } catch (WorldClockException e) {
            // Tratamento de erro específico capturado do serviço
            model.addAttribute("error", "Erro ao buscar o horário: " + e.getMessage());
        } catch (Exception e) {
            // Tratamento genérico de outros erros
            model.addAttribute("error", "Erro inesperado ao processar a requisição.");
        }

        return "time"; // Nome do template Thymeleaf (time.html)
    }

    /// Metodo responsável por fornecer o horário atual em formato JSON.
    ///
    /// Este endpoint lida com requisições GET na rota "/api/time" e retorna um JSON com o horário UTC e o horário
    /// local no formato ISO 8601. Caso ocorra algum erro ao processar a data/hora ou a API não esteja acessível,
    /// uma mensagem de erro é retornada no JSON.
    ///
    /// @return Um mapa com os horários UTC e local ou uma mensagem de erro.
    @GetMapping("/api/time")
    @ResponseBody
    public Map<String, String> getTimeApi() {
        Map<String, String> timeData = new HashMap<>();

        try {
            WorldClockResponse response = worldClockService.getCurrentUtcTime();

            if (response != null && response.utcDateTime() != null) {
                // Parse da data/hora UTC
                ZonedDateTime utcDateTime = ZonedDateTime.parse(response.utcDateTime());

                // Conversão para o horário local
                ZonedDateTime localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault());

                // Formatação das datas em ISO 8601
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

                // Adiciona os horários ao mapa
                timeData.put("utcTime", utcDateTime.format(formatter));
                timeData.put("localTime", localDateTime.format(formatter));
            } else {
                // Caso a resposta seja nula, adiciona uma mensagem de erro ao mapa
                timeData.put("error", "Não foi possível obter o horário no momento.");
            }
        } catch (WorldClockException e) {
            // Tratamento de erro específico capturado do serviço
            timeData.put("error", "Erro ao buscar o horário: " + e.getMessage());
        } catch (Exception e) {
            // Tratamento genérico de outros erros
            timeData.put("error", "Erro inesperado ao processar a requisição.");
        }

        return timeData;
    }
}
