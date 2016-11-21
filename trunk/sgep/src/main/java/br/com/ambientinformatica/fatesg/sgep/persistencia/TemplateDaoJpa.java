package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("templateDao")
public class TemplateDaoJpa extends PersistenciaJpa<Template> implements TemplateDao{
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Template> consultarPelaDescricao(String descricao) {
		Session session = this.em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Template.class);
		
		if (StringUtils.isNotBlank(descricao)) {
			criteria.add(Restrictions.ilike("descricao",descricao.toUpperCase(),MatchMode.START));
		}
		return criteria.list();
	}

	@Override
	public Template carregarTemplate(Template template) {
		try {
			Query query = em.createQuery("select t from Template t "
					+ " left join fetch t.sessoes sest "
					+ " left join fetch sest.sessao ses "
					+ " left join fetch sest.itensQuestao ite"
					+ " left join fetch ite.sessao ses1 "
					+ " left join fetch ite.questaoTemplate quet "
					+ " left join fetch quet.questao que "
					+ " where t = :template");
			query.setParameter("template", template);
			return (Template) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
