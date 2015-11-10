package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface AlunoDao extends Persistencia<Aluno> {

	public List<Aluno> listarPorNome(String nome);
	
	public List<Aluno> listarTodos() throws Exception;
}
