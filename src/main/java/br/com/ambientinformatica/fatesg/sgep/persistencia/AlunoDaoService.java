package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.XStream;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.sgep.util.PropertiesLoader;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("alunoDao")
public class AlunoDaoService extends PersistenciaJpa<Aluno> implements AlunoDao, Serializable {
	private static final long serialVersionUID = 1L;

	private PropertiesLoader loader = new PropertiesLoader();
	private Client client = ClientBuilder.newClient();
	private WebTarget target = client.target(loader.getValor("sgep.properties", "urlServicoCorporatum") + "aluno");


	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/aluno")
	public List<Aluno> listarPorNome(String nome) {
		String conteudo = target.path("/listarPorNome/" + nome).request().get(String.class);
		return (List<Aluno>) new XStream().fromXML(conteudo);
	}

}
