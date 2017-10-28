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

import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("cursoDao")
public class CursoDaoService extends PersistenciaJpa<Curso> implements CursoDao, Serializable {

	private Client client = ClientBuilder.newClient();

	private WebTarget target = client.target("http://inpai.com.br/corporatum/service/curso");

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/curso")
	public List<Curso> listarPorNome(String nome) {
		String conteudo = target.path("/listaPorNome/" + nome).request().get(String.class);
		return (List<Curso>) new XStream().fromXML(conteudo);
	}

	@Override
	public Curso consultarPorChaveCursoCorporatum(Integer id) throws PersistenciaException{
		try {
			String sql ="select c from Curso c where c.chaveCursoCorporatum = :id";
			Query query = em.createQuery(sql);
			query.setParameter("id", id);
			return (Curso) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/curso")
	public List<Curso> listarCursos() throws PersistenciaException {
		String conteudo = target.path("/listarCursos").request().get(String.class);
		return (List<Curso>) new XStream().fromXML(conteudo);
	}

}
