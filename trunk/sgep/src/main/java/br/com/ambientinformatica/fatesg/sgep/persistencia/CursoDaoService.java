package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.XStream;

import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("cursoDao")
public class CursoDaoService extends PersistenciaJpa<Curso> implements CursoDao, Serializable {

	private Client client = ClientBuilder.newClient();

	private WebTarget target = client.target("http://localhost:8080/corporatum/service/curso");

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/curso")
	public List<Curso> listarPorNome(String nome) {
		String conteudo = target.path("/listaPorNome/" + nome).request().get(String.class);
		return (List<Curso>) new XStream().fromXML(conteudo);
	}

}
