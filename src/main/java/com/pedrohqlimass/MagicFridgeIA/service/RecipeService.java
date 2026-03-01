package com.pedrohqlimass.MagicFridgeIA.service;

import com.pedrohqlimass.MagicFridgeIA.model.FoodItemModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;


    public RecipeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItemModel> foodItemModels) {

        String alimentos = foodItemModels.stream()
                .map(item -> String.format("%s (%s) - Quantidade: %d, Validade: %s",
                        item.getNome(), item.getFoodItemCategory(), item.getQuantidade(), item.getValidade()))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado no meu banco de dados faça um receita com os seguintes alimentos:\n " + alimentos;

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
