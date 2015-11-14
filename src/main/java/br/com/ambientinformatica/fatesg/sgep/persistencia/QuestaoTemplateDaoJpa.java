package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("questaoTemplateDao")
public class QuestaoTemplateDaoJpa extends PersistenciaJpa<QuestaoTemplate> implements QuestaoTemplateDao {

	private static final long serialVersionUID = 1L;
	
	

}
