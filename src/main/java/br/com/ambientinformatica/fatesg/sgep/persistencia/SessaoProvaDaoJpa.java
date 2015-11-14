package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoProva;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("sessaoProvaDao")
public class SessaoProvaDaoJpa extends PersistenciaJpa<SessaoProva> implements
		SessaoProvaDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<SessaoProva> consultarPeloTitulo(String titulo) {
		Session session = this.em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(SessaoProva.class);

		if (StringUtils.isNotBlank(titulo)) {
			criteria.add(Restrictions.ilike("titulo", titulo.toUpperCase(),
					MatchMode.START));
		}
		return criteria.list();
	}

}
