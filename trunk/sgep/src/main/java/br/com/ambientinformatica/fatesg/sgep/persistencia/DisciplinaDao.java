package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface DisciplinaDao extends Persistencia<Disciplina> {

	public List<Disciplina> listarPorNome(String nome);
	
	public List<Disciplina> listarTodos() throws Exception;
}
