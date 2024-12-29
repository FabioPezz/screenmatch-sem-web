package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private final String dominioUrl = "https://www.omdbapi.com/?t=";

    private final String apiKey = "&apikey=6585022c";

    ConsumoApi consumoApi = new ConsumoApi();

    ConverteDados conversor = new ConverteDados();

    public void exibirMenu(){

        System.out.println("Insira o nome da séria para busca :");

        var nomeSerie = leitura.nextLine();

        String url = dominioUrl + nomeSerie.replace(" ","+") + apiKey;

        String json = consumoApi.obterDados(url);

        DadosSerie infosSerie = conversor.obterDados(json, DadosSerie.class);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= infosSerie.totalTemporadas(); i++){

            json = consumoApi.obterDados(dominioUrl + nomeSerie.replace(" ","+") +"&season="+ i + apiKey);

			DadosTemporada infosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(infosTemporada);

		}


        List<String> nomesEpisodios = new ArrayList<>();

        temporadas.forEach(t -> t.episodios().forEach(e -> nomesEpisodios.add(e.titulo())));
        //UTILIZANDO LAMBDA EM COLEÇÃO

        temporadas.forEach(System.out::println);

        System.out.println(nomesEpisodios);
        System.out.println(infosSerie);
        System.out.println(temporadas);

    }

}
