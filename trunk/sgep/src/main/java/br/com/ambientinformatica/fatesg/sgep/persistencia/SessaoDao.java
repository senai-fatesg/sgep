package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.sgep.entidade.Sessao;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface SessaoDao extends Persistencia<Sessao> {
	
	List<Sessao> consultarPeloTitulo(String nome);

}
