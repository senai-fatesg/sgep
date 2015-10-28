package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;

@Repository("disciplinaDao")
public class DisciplinaDaoService implements DisciplinaDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listarPorNome(String nome) {
		return restTemplate.getForObject("http://localhost:8180/corporatum/service/colaborador/consultarPorNome/" + nome, List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listarTodos() throws Exception {
		return restTemplate.getForObject("http://localhost:8180/corporatum/service/disciplina/listarTodos/", List.class);
	}
	
	
}
