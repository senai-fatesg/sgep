package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Sessao;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("sessaoDao")
public class SessaoDaoJpa extends PersistenciaJpa<Sessao> implements SessaoDao{
	private static final long serialVersionUID = 1L;

}
