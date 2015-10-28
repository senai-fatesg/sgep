package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

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

}
