package com.pedrohqlimass.MagicFridgeIA.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;


    public RecipeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe() {
        String prompt = "Me sugira receitas simples com ingredientes comuns";

        // cria o prompt
        Map<String, Object> requestBody = Map.of(
                "system_instruction", Map.of(
                        "parts", Map.of("text", "Você é um assistênte que cria recitas.")
                ),
                "contents", List.of(
                        Map.of(
                                "role", "user",
                                "parts", List.of(
                                        Map.of(
                                        "text", prompt)
                                )
                        )
                )
        );

        // envia o prompt
        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-goog-api-key",  apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class)
                                .doOnNext(body -> System.out.println("ERRO GEMINI: " + body))
                                .flatMap(body -> Mono.error(new RuntimeException("Erro Gemini: " + body)))
                ) // intercepta erros 4xx(ClientError) e loga o body da resposta antes de lançar a exceção
                .bodyToMono(Map.class)
                .map(response ->  {
                    var candidates = (List<Map<String, Object>>) response.get("candidates");

                    if (candidates != null && !candidates.isEmpty()) {
                        var content = (Map<String, Object>) candidates.get(0).get("content");
                        var parts = (List<Map<String, Object>>) content.get("parts");

                        if (parts != null && !parts.isEmpty()) {
                            return parts.get(0).get("text").toString();
                        }
                    }
                    return "Nenhuma receita foi gerada";
                });
    }
}
