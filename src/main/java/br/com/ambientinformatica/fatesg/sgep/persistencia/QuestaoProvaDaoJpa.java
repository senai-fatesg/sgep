package br.com.ambientinformatica.fatesg.sgep.persistencia;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoProva;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("questaoProvaDao")
public class QuestaoProvaDaoJpa extends PersistenciaJpa<QuestaoProva> implements QuestaoProvaDao {

	private static final long serialVersionUID = 1L;

	@Override
	public QuestaoProva consultarAlternativasQuestao(QuestaoProva questaoProva) throws PersistenciaException {
		try {
			String sql = "select distinct qp from QuestaoProva qp left join fetch qp.questao q left join fetch q.professor p left join fetch q.alternativas a where q.id = :id";
			Query query = em.createQuery(sql);
			if (questaoProva != null) {
				query.setParameter("id", questaoProva.getId());
			}
			return (QuestaoProva) query.getSingleResult();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}

	@Override
	public QuestaoProva consultarEnunciadoQuestao(QuestaoProva questaoProva) throws PersistenciaException {
		try {
			String sql = "select distinct qp from QuestaoProva qp left join fetch qp.questao q left join fetch q.professor p left join fetch q.alternativas a where q.id = :id";
			Query query = em.createQuery(sql);
			if (questaoProva != null) {
				query.setParameter("id", questaoProva.getId());
			}
			return (QuestaoProva) query.getSingleResult();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}

}
