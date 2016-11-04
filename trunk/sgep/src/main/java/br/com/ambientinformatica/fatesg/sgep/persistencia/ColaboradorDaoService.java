package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

import com.thoughtworks.xstream.XStream;

@Repository("colaboradorDao")
public class ColaboradorDaoService extends PersistenciaJpa<Colaborador> implements ColaboradorDao, Serializable {

	private Client client = ClientBuilder.newClient();

	private WebTarget target = client.target("http://localhost:8080/corporatum/service/colaborador");

	private static final long serialVersionUID = 1L;

	@Override
	@RequestMapping("/colaborador")
	public Colaborador consultarPorCpf(String cpf) {
		String conteudo = target.path("/listarPorCPF/" + cpf).request().get(String.class);
		return (Colaborador) new XStream().fromXML(conteudo);
	}

	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/colaborador")
	public List<Colaborador> listarPorNome(String nome) {
		String conteudo = target.path("/listarPorNome/" + nome).request().get(String.class);
		return (List<Colaborador>) new XStream().fromXML(conteudo);
	}
}
