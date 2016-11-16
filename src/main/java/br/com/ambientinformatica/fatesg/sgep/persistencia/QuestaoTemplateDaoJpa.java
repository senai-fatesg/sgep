package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("questaoTemplateDao")
public class QuestaoTemplateDaoJpa extends PersistenciaJpa<QuestaoTemplate>
		implements QuestaoTemplateDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestaoTemplate> consultarPor(String palavra, String tipo) {
		try {
			String jpaql = "select distinct qt from QuestaoTemplate qt "
					+ " left join fetch qt.questao q ";
			if (!palavra.isEmpty() || palavra != null) {
				if (!tipo.isEmpty() || tipo != null) {
					if (tipo == "Dificuldade") {
						jpaql += " where upper(q.dificuldade) like :dificuldade";
					}
					if (tipo == "Assunto") {
						jpaql += " where upper(q.assunto) like :assunto";
					}
					if (tipo == "Disciplina") {
						jpaql += " where upper(q.disciplina) like :disciplina";
					}
				}
			}
			Query query = em.createQuery(jpaql);
			if (!palavra.isEmpty() || palavra != null) {
				if (tipo.isEmpty() || tipo == null) {
					if (tipo == "Dificuldade") {
						query.setParameter("dificuldade",
								"%" + palavra.toUpperCase() + "%");
					}
					if (tipo == "Assunto") {
						query.setParameter("assunto",
								"%" + palavra.toUpperCase() + "%");
					}
					if (tipo == "Disciplina") {
						query.setParameter("disciplina",
								"%" + palavra.toUpperCase() + "%");
					}
				}
			}
			return (List<QuestaoTemplate>) query.getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<QuestaoTemplate>();
		}
	}

	@Override
	public QuestaoTemplate carregarQuestao(QuestaoTemplate questao) throws Exception {
		Query q = em.createQuery("from QuestaoTemplate as qt "
				+ " left join fetch qt.questao que "
				+ " left join fetch que.professor pro"
				+ " where qt = :questao");
		q.setParameter("questao", questao);
		return (QuestaoTemplate) q.getSingleResult();
	}

	@Override
	public List<QuestaoTemplate> listarQuestoes() throws Exception {
		try {
			Query query = em.createQuery("select qt from QuestaoTemplate qt left join fetch qt.questao q left join fetch q.professor p");
			return query.getResultList();
		} catch (Exception e) {
			System.out.println(e);
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}
	
	@Override
	public QuestaoTemplate consultarAlternativasQuestao(QuestaoTemplate questaoTemplate) {
		try {
			String sql = "select qt from QuestaoTemplate qt left join fetch qt.questao q left join fetch q.professor p left join fetch q.alternativas a where q.id = :id";
			Query query = em.createQuery(sql);
			if (questaoTemplate != null) {
				query.setParameter("id", questaoTemplate.getId());
			}
			return (QuestaoTemplate) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}
	
}
