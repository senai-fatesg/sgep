package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;

@Repository("colaboradorDao")
public class ColaboradorDaoService implements ColaboradorDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();

	private static final long serialVersionUID = 1L;

	@Override
	public Colaborador consultarPorCpf(String cpf) {

		@SuppressWarnings("unchecked")
      Colaborador colaborador = restTemplate.getForObject("http://localhost:8080/corporatum/service/colaborador/consultarPorCpf/"+cpf, Colaborador.class);

		return colaborador;
	}
}
