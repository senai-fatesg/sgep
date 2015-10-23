package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Curso;

public interface CursoDao {

	public List<Curso> listarPorNome(String nome);
	
	public List<Curso> listarTodos() throws Exception;
}
