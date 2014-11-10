package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("provaDao")
public class ProvaDaoJpa extends PersistenciaJpa<Prova> implements ProvaDao {

	private static final long serialVersionUID = 1L;

}
