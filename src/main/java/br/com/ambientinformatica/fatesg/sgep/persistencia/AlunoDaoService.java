package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("alunoDao")
public class AlunoDaoService extends PersistenciaJpa<Aluno> implements
		AlunoDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> listarPorNome(String nome) {
		return restTemplate.getForObject(
				"http://localhost:8180/corporatum/service/aluno/listarPorNome/"
						+ nome, List.class);
	}

}
