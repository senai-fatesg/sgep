package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;

public interface DisciplinaDao {

	public List<Disciplina> listarPorNome(String nome);
	
	public List<Disciplina> listarTodos() throws Exception;
}
