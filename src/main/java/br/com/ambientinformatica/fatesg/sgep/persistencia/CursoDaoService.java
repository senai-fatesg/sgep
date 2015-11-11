package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("cursoDao")
public class CursoDaoService extends PersistenciaJpa<Curso> implements CursoDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> listarPorNome(String nome) {
		return (List<Curso>) restTemplate.getForObject("http://localhost:8180/corporatum/service/curso/listaPorNome/"+nome, List.class);
	}

	
	
}
