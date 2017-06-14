package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.util.UtilLog;

@Repository("provaDao")
public class ProvaDaoJpa extends PersistenciaJpa<Prova> implements ProvaDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Prova> listarProvas() throws PersistenciaException {
		String sql = ("select distinct p from Prova p left join fetch p.curso c left join fetch p.disciplina d left join fetch p.instituicao i left join fetch p.sessoes s");
		Query query = em.createQuery(sql);
		return query.getResultList();
	}

	@Override
	public Prova consultarProva(Integer id) throws PersistenciaException {
		try {
			if (id == null) {
				throw new Exception("Erro ao consultar a prova selecionada");
			}
			String sql = ("select distinct p from Prova p left join fetch p.curso c left join fetch p.disciplina d left join fetch p.instituicao i left join fetch p.sessoes s where p.id = :id");
			Query query = em.createQuery(sql);
			query.setParameter("id", id);
			return (Prova) query.getSingleResult();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException("Erro ao consultar sessão.", e);
		}
	}

}
