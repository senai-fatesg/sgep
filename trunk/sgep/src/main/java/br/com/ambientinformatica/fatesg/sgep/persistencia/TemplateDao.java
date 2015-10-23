package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface TemplateDao extends Persistencia<Template>{

	List<Template> consultarPelaDescricao(String descricao);
}
