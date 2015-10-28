package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;

public interface InstituicaoDao {

	public List<Instituicao> listarPorNome(String nome);
	
	public List<Instituicao> listarTodos() throws Exception;
}
