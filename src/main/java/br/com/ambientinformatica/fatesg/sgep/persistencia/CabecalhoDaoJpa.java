package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Cabecalho;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("cabecalhoDao")
public class CabecalhoDaoJpa extends PersistenciaJpa<Cabecalho> implements CabecalhoDao {

	private static final long serialVersionUID = 1L;

}
