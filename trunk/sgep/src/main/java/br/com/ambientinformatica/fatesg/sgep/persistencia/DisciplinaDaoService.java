package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

import com.thoughtworks.xstream.XStream;

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

}
