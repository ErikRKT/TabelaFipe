package br.com.alura.TabelaFipe.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


public class DataConversor implements IConversor {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classeDestino) {
        try {
            return objectMapper.readValue(json, classeDestino);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage());
        }
    }


    @Override
    public <T> List<T> converterParaLista(String json, Class<T> classeDestino) {
        CollectionType lista = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, classeDestino);
        try {
            return objectMapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter JSON para lista: " + e.getMessage());
        }
    }

}
