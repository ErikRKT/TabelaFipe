package br.com.alura.TabelaFipe.services;

import java.util.List;

public interface IConversor {
    <T> T obterDados(String json, Class<T> classeDestino);
    <T> List<T> converterParaLista(String json, Class<T> classeDestino);
}