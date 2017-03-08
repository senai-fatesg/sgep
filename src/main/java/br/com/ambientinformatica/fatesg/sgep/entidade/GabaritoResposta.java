/*
 * Classe responsável por gerar o resultado da prova (Gabarito com respostas)
 * 
 * */

package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class GabaritoResposta {
	private HashMap questoes;
	
	List<List<Map.Entry<Integer, String>>> dataTableDividido;

	List<QuestaoTemplate> listaQuestaoTemplate;

	public GabaritoResposta() {

	}

	//Gera respostas não divididas em sublistas a partir do hashmap de questoes
	public List<Map.Entry<Integer, String>> getRespostas() {
		@SuppressWarnings("unchecked")
		Set<Map.Entry<Integer, String>> respostas = this.getQuestoes() != null
				? this.getQuestoes().entrySet()
				: new HashMap<>().entrySet();
		return new ArrayList<Map.Entry<Integer, String>>(respostas);
	}
	
	//Gera lista dividida em sublistas a partir da lista de respostas
	public List<List<Map.Entry<Integer, String>>> getDataTableDividido() {
		List<Map.Entry<Integer, String>> respostas = this.getRespostas();
		if(respostas.size() == 0)
			return new ArrayList<List<Map.Entry<Integer, String>>>();
		
		List<List<Map.Entry<Integer, String>>> listaRetorno = new ArrayList<>();
		List<Map.Entry<Integer, String>> respostasAux = new ArrayList<>();
		int qtdQuestoesPag = 10;
		int qtdPagina = 1;
		int j = 0;
		for (int i = 0; i <= respostas.size(); i++) {
			if (i < qtdQuestoesPag && i < respostas.size()) {
				respostasAux.add(j, respostas.get(i));
				j++;
			} else {
				listaRetorno.add(respostasAux);
				respostasAux = new ArrayList<>();
				if (i < respostas.size()) {
					respostasAux.add(0, respostas.get(i));
					qtdQuestoesPag += 10;
					qtdPagina += 1;
					j = 1;
				}
			}
		}
		return this.dataTableDividido = listaRetorno;
	}

	public HashMap getQuestoes() {
		return this.questoes;
	}

	public void setQuestoes(HashMap questoes) {
		this.questoes = questoes;
	}

	public List<QuestaoTemplate> getListaQuestaoTemplate() {
		return listaQuestaoTemplate;
	}

	public void setGabarito(List<QuestaoTemplate> listaQuestaoTemplate) {
		this.listaQuestaoTemplate = listaQuestaoTemplate;
	}

}
