package br.com.alura.TabelaFipe.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


import br.com.alura.TabelaFipe.model.Data;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.services.ApiConsume;
import br.com.alura.TabelaFipe.services.DataConversor;

public class main {
    
    
    private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1";
    private Scanner scanner = new Scanner(System.in);
    private ApiConsume apiConsume = new ApiConsume();
    private DataConversor dataConversor = new DataConversor();

    public void exibeMenu(){
        var menu = """
                1 - Listar marcas de carros
                2 - Listar modelos de uma marca
                3 - Listar anos de um modelo
                4 - Consultar preço de um veículo
                0 - Sair
                """;
        System.out.println(menu);
        var opcao = scanner.nextLine();
        String endereco;

        if (opcao.equals("1") || opcao.toLowerCase().contains("carr")){
            endereco = BASE_URL + "/carros/marcas";
        } else if (opcao.equals("2") || opcao.toLowerCase().contains("mot")){
            endereco = BASE_URL + "/motos/marcas";
        } else {
            endereco = BASE_URL + "/caminhoes/marcas";
        }


        var json = apiConsume.obterDados(endereco);
        System.out.println(json);
        var marcas = dataConversor.converterParaLista(json, Data.class);
        marcas.stream()
                 .sorted(Comparator.comparing(Data::codigo))
                 .forEach(System.out::println);

        System.out.println("Digite o código da marca desejada:");
        var codigoMarca = scanner.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = apiConsume.obterDados(endereco);
        var modeloLista = dataConversor.obterDados(json, br.com.alura.TabelaFipe.model.Models.class);
        System.out.println("Modelos disponíveis:");
        modeloLista.modelos()
                     .stream()
                     .sorted(Comparator.comparing(Data::nome))
                     .forEach(System.out::println);

        System.out.println("Digite o nome do carro desejado:");
        var nomeVeiculo = scanner.nextLine();

        List<Data> modelosFiltrados = modeloLista.modelos()
            .stream()
            .filter(modelo -> modelo.nome().equalsIgnoreCase(nomeVeiculo))
            .toList();
        
        System.out.println("Modelos encontrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo desejado:");
        var codigoModelo = scanner.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = apiConsume.obterDados(endereco);

        List<Data> anos = dataConversor.converterParaLista(json, Data.class);
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            var jsonVeiculo = apiConsume.obterDados(enderecoAnos);
            Veiculo veiculo = dataConversor.obterDados(jsonVeiculo, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("Veículos disponíveis:");
        veiculos.forEach(System.out::println);
    }
}
