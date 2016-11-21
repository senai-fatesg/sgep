package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("provaDao")
public class ProvaDaoJpa extends PersistenciaJpa<Prova> implements ProvaDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Prova> listarProvas() throws PersistenciaException {
		String sql = ("select p from Prova p left join fetch p.curso c left join fetch p.disciplina d left join fetch p.instituicao i left join fetch p.sessoes s");
		Query query = em.createQuery(sql);
		return query.getResultList();
	}

}
