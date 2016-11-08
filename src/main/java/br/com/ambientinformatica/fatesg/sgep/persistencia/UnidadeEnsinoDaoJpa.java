package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.api.entidade.UnidadeEnsino;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("unidadeEnsinoDao")
public class UnidadeEnsinoDaoJpa extends PersistenciaJpa<UnidadeEnsino> implements UnidadeEnsinoDao{

	private static final long serialVersionUID = 1L;

}
