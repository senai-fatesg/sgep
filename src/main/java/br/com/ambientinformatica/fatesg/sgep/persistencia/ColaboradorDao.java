package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;

public interface ColaboradorDao {
	
	public Colaborador consultarPorCpf(String cpf);

	public List<Colaborador> listarTodos() throws Exception;
	
}