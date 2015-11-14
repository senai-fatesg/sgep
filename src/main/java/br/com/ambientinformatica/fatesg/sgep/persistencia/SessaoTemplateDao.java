package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface SessaoTemplateDao extends Persistencia<SessaoTemplate> {
	
	List<SessaoTemplate> consultarPeloTitulo(String nome);

}
