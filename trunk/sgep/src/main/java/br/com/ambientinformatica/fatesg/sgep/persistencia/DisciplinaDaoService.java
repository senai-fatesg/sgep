package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("disciplinaDao")
public class DisciplinaDaoService extends PersistenciaJpa<Disciplina> implements
		DisciplinaDao, Serializable {

	private Client client = ClientBuilder.newClient();

	private WebTarget target = client.target("http://localhost:8080/corporatum/service/disciplina");


	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listarPorNome(String nome) {
		String conteudo = target.path("/listarPorNome/" + nome).request().get(String.class);
		return (List<Disciplina>) new XStream().fromXML(conteudo);
	}

}
