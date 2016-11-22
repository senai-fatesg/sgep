package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface DisciplinaDao extends Persistencia<Disciplina> {

	public List<Disciplina> listarPorNome(String nome);

	public Disciplina consultarPorChaveDisciplinaCorporatum(Integer id);

	public List<Disciplina> listarDisciplinas() throws PersistenciaException;
}
