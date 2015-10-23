package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Curso;

@Repository("cursoDao")
public class CursoDaoService implements CursoDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();
	
	private static final long seralVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> listarPorNome(String nome) {
		return (List<Curso>) restTemplate.getForObject("http://localhost:8180/corporatum/service/curso/consultarPorNome/"+nome, List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> listarTodos() throws Exception {
		return (List<Curso>) restTemplate.getForObject("http://localhost:8180/corporatum/service/curso/listarTodos", List.class);
	}
	
	
}
