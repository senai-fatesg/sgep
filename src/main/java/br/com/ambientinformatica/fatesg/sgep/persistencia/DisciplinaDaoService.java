package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.XStream;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.sgep.util.PropertiesLoader;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("disciplinaDao")
public class DisciplinaDaoService extends PersistenciaJpa<Disciplina> implements DisciplinaDao, Serializable {
	private static final long serialVersionUID = 1L;
	
	private PropertiesLoader loader = new PropertiesLoader();
	private Client client = ClientBuilder.newClient();
	private WebTarget target = client.target(loader.getValor("sgep.properties", "urlServicoCorporatum") + "disciplina");


	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listarPorNome(String nome) {
		String conteudo = target.path("/listarPorNome/" + nome).request().get(String.class);
		return (List<Disciplina>) new XStream().fromXML(conteudo);
	}

	@Override
	public Disciplina consultarPorChaveDisciplinaCorporatum(Integer id) throws PersistenciaException{
		try {
			String sql ="select d from Disciplina d where d.chaveDisciplinaCorporatum = :id";
			Query query = em.createQuery(sql);
			query.setParameter("id", id);
			return (Disciplina) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/disciplina")
	public List<Disciplina> listarDisciplinas() throws PersistenciaException {
		String conteudo = target.path("/listarDisciplinas").request().get(String.class);
		return (List<Disciplina>) new XStream().fromXML(conteudo);
	}


}
