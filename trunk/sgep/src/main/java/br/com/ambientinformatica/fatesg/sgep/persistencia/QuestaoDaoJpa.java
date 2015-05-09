package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Questao;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("questaoDao")
public class QuestaoDaoJpa extends PersistenciaJpa<Questao> implements QuestaoDao {

	private static final long serialVersionUID = 1L;
	
	

}
