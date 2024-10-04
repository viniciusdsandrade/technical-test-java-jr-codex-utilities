package com.restful.rest.server.world.clock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;


@Configuration("JacksonConfig")
public class JacksonConfig {

    /// Configura um 'ObjectMapper' personalizado para serialização e desserialização de objetos JSON,
    /// incluindo suporte para tipos de data e hora da API Java 8 ('java.time').
    ///
    /// Esta classe define um bean de configuração Spring que retorna uma instância de 'ObjectMapper'.
    /// O 'ObjectMapper' configurado permite lidar com a serialização correta de tipos de data/hora,
    /// desativando a serialização como timestamps e registrando o módulo 'JavaTimeModule'.
    ///
    /// @return Uma instância configurada de `ObjectMapper`.
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper(); // Cria uma nova instância de 'ObjectMapper' usada para serializar e desserializar JSON.
        mapper.registerModule(new JavaTimeModule()); // Registra o módulo 'JavaTimeModule' para suportar a serialização de tipos de data e hora da API Java 8 ('java.time').
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);   // Desativa a serialização de datas como timestamps (números inteiros) e configura o 'ObjectMapper' para usar o formato legível de data/hora (padrão ISO-8601).

        return mapper;
    }
}

