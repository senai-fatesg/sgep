package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;

@Repository("colaboradorDao")
public class ColaboradorDaoService implements ColaboradorDao, Serializable {

	private RestTemplate restTemplate = new RestTemplate();

	private static final long serialVersionUID = 1L;

	@Override
	public Colaborador consultarPorCpf(String cpf) {

		return restTemplate.getForObject("http://localhost:8080/corporatum/service/colaborador/consultarPorCpf/"+cpf, Colaborador.class);
	}
	
	
   @SuppressWarnings("unchecked")
   public List<Colaborador> listarTodos() throws Exception{
//   	return restTemplate.getForObject("http://localhost:8080/corporatum/service/colaborador/listarTodos/", List.class);
   	return new ArrayList<Colaborador>();
	}
}
