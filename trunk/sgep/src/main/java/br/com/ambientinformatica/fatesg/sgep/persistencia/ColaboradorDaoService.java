package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("colaboradorDao")
public class ColaboradorDaoService extends PersistenciaJpa<Colaborador>
		implements ColaboradorDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();

	private static final long serialVersionUID = 1L;

	@Override
	@RequestMapping("/colaborador")
	public Colaborador consultarPorCpf(String cpf) {
		return restTemplate.getForObject(
				"http://localhost:8180/corporatum/service/colaborador/listarPorCPF/"
						+ cpf, Colaborador.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Colaborador> listarPorNome(String nome) {
		return restTemplate.getForObject(
				"http://localhost:8180/corporatum/service/colaborador/listarPorNome/"
						+ nome, List.class);
	}
}
