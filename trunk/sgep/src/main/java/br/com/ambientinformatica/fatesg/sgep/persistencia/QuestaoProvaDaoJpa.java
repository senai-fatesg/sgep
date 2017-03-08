package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoProva;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("questaoProvaDao")
public class QuestaoProvaDaoJpa extends PersistenciaJpa<QuestaoProva> implements QuestaoProvaDao {

	private static final long serialVersionUID = 1L;

}
