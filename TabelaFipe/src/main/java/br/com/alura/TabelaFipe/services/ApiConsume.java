package br.com.alura.TabelaFipe.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class ApiConsume {
    public String obterDados(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao consumir a API: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Requisição interrompida: " + e.getMessage());
        }
        String json = response.body();
        return json;
    }
}
