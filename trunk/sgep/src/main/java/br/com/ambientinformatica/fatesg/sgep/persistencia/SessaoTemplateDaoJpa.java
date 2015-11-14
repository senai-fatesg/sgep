package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("sessaoTemplateDao")
public class SessaoTemplateDaoJpa extends PersistenciaJpa<SessaoTemplate> implements SessaoTemplateDao {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SessaoTemplate> consultarPeloTitulo(String titulo){
		Session session = this.em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(SessaoTemplate.class);
		
		if (StringUtils.isNotBlank(titulo)) {
			criteria.add(Restrictions.ilike("titulo", titulo.toUpperCase(), MatchMode.START));
		}
		return criteria.list();
	}
}
