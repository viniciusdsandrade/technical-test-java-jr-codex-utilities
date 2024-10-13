package com.restful.rest.client.world.clock.service.impl;

import com.restful.rest.client.world.clock.exception.WorldClockException;
import com.restful.rest.client.world.clock.exception.WorldClockHttpException;
import com.restful.rest.client.world.clock.model.WorldClockResponse;
import com.restful.rest.client.world.clock.service.WorldClockService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

/// Implementação da classe 'WorldClockServiceImpl', responsável por fornecer a funcionalidade de buscar a hora atual
/// no formato UTC (Tempo Universal Coordenado) por uma chamada para uma API externa.
///
/// Esta classe utiliza a biblioteca 'RestTemplate' do Spring para fazer requisições HTTP e se comunicar com
/// o servidor da API World Clock. Ela segue o padrão de serviço no contexto da arquitetura Spring Boot,
/// anotada com '@Service' para ser gerenciada pelo container de IoC (Inversão de Controle) do Spring.
@Service
public class WorldClockServiceImpl implements WorldClockService {

    private final RestTemplate restTemplate;

    /// Construtor da classe 'WorldClockServiceImpl'.
    ///
    /// Este construtor inicializa uma nova instância de 'RestTemplate', que é a classe responsável por realizar
    /// as requisições HTTP para serviços RESTful.
    ///
    /// @implNote O 'RestTemplate' aqui criado pode ser substituído por uma instância mais configurável ou mesmo
    /// por outra solução de comunicação, caso seja necessário alterar a forma de realizar as requisições HTTP.
    public WorldClockServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    /// Implementação do metodo 'getCurrentUtcTime' definido na interface 'WorldClockService'.
    ///
    /// Este metodo faz uma chamada HTTP GET para a API de tempo em '[...](https://worldtimeapi.org/api/timezone/Etc/UTC)',
    /// recuperando a hora atual no formato UTC (Tempo Universal Coordenado).
    ///
    /// - Se a chamada for bem-sucedida, o metodo retorna uma instância de 'WorldClockResponse' contendo os dados
    ///   recebidos da API.
    /// - Em caso de falha devido a um erro HTTP (problemas de conexão, status HTTP de erro, etc.), o código lança
    ///   a exceção 'WorldClockHttpException', fornecendo uma mensagem clara sobre o erro ocorrido e o status HTTP.
    /// - Qualquer outra exceção que não esteja relacionada a HTTP será encapsulada e lançada como 'WorldClockException',
    ///   permitindo um tratamento genérico de outros tipos de falhas, como parsing ou problemas no servidor.
    ///
    /// @return Uma instância de 'WorldClockResponse' com a hora UTC se a requisição for bem-sucedida.
    /// @implSpec Este mwtodo utiliza o 'RestTemplate' para fazer a requisição e lança exceções específicas
    /// ('WorldClockHttpException' e 'WorldClockException') para diferenciar os tipos de erro.
    @Override
    public WorldClockResponse getCurrentUtcTime() throws WorldClockException {
        String url = "https://worldtimeapi.org/api/timezone/Etc/UTC";
        try {
            return restTemplate.getForObject(url, WorldClockResponse.class);
        } catch (HttpClientErrorException e) {
            throw new WorldClockHttpException("Erro ao buscar o horário UTC: " + e.getStatusCode(), e); // Lança uma exceção específica para erros HTTP
        } catch (Exception e) {
            throw new WorldClockException("Erro ao buscar o horário UTC", e); // Lança uma exceção geral para outros erros
        }
    }
}
