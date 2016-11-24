package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface ColaboradorDao extends Persistencia<Colaborador> {

	public List<Colaborador> listarPorNome(String nome);

	public Colaborador consultarPorCpf(String cpf);
	
	public Colaborador consultarPorCpfSgep(String cpf);
	
	public Colaborador consultarPorIdPaiSgep(int idPai) throws PersistenciaException;

}