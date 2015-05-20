package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("templateDao")
public class TemplateDaoJpa extends PersistenciaJpa<Template> implements TemplateDao{
	
	private static final long serialVersionUID = 1L;

}
