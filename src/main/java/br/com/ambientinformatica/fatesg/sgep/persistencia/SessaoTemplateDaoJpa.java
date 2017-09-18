package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.util.UtilLog;

@Repository("sessaoTemplateDao")
public class SessaoTemplateDaoJpa extends PersistenciaJpa<SessaoTemplate> implements SessaoTemplateDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<SessaoTemplate> consultarPeloTitulo(String titulo) throws PersistenceException{
		Session session = this.em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(SessaoTemplate.class);

		if (StringUtils.isNotBlank(titulo)) {
			criteria.setProjection(Projections.distinct(Projections.property("titulo")));
			criteria.add(Restrictions.ilike("sessao.titulo", titulo.toUpperCase(), MatchMode.START));
		}
		return criteria.list();
	}

	public List<SessaoTemplate> pesquisarPorTituloOuDescricao(String titulo) throws PersistenceException{
		try {
			String sql = "from SessaoTemplate st left join fetch st.itensQuestao i left join fetch st.sessao s where 1 = 1";
			if (titulo != null && !titulo.isEmpty()) {
				sql += " and UPPER(s.titulo) like :titulo";
			}
			Query query = em.createQuery(sql);
			if (titulo != null && !titulo.isEmpty()) {
				query.setParameter("titulo", "%" + titulo.toUpperCase() + "%");
			}
			return query.getResultList();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException("Erro ao consultar sessão.", e);
		}
	}

	@Override
	public List<SessaoTemplate> listarSessoes() throws PersistenceException {
		try {
			String sql = "select st from SessaoTemplate st left join fetch st.itensQuestao";
			Query query = em.createQuery(sql);
			return query.getResultList();

		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException("Erro ao listar sessões.", e);
		}
	}

}
