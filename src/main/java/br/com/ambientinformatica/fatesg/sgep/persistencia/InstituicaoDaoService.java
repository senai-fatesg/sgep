package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;

@Repository("instituicaoDao")
public class InstituicaoDaoService implements InstituicaoDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarPorNome(String nome) {
		return restTemplate.getForObject("http://localhost:8180/corporatum/service/instituicao/consultarPorNome/" + nome, List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarTodos() throws Exception {
		return restTemplate.getForObject("http://localhost:8180/corporatum/service/instituicao/listarTodos/", List.class);
	}
	
	
}
