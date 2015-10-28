package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;

public interface ColaboradorDao {
	
	public List<Colaborador> listarPorNome(String nome);
	
	public Colaborador consultarPorCpf(String cpf);

	public List<Colaborador> listarTodos() throws Exception;
	
}