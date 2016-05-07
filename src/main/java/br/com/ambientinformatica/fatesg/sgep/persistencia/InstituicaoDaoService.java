package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;

import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("instituicaoDao")
public class InstituicaoDaoService extends PersistenciaJpa<Instituicao>
		implements InstituicaoDao, Serializable {
	
	private Client client = ClientBuilder.newClient();

	private WebTarget target = client.target("http://localhost:8080/corporatum/service/instituicao");

	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarPorNome(String nome) {
		String conteudo = target.path("/listarPorNome/" + nome).request().get(String.class);
		return (List<Instituicao>) new XStream().fromXML(conteudo);
	}

}
