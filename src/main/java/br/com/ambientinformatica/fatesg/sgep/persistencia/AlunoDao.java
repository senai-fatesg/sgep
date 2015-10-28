package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;

public interface AlunoDao {

	public List<Aluno> listarPorNome(String nome);
	
	public List<Aluno> listarTodos() throws Exception;
}
